/*
 * Copyright (C) 2013 Peter Withers
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.bambooradical.scratchbuilt.serialisers;

import com.bambooradical.scratchbuilt.data.ModelData;
import com.bambooradical.scratchbuilt.data.WingSegment;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created on : Oct 07, 2013, 15:39 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class GcodeWing extends Gcode {

    public GcodeWing(ModelData modelData) {
        super(modelData);
    }
    final private String aerofoilDataFile = "/AerofoilData/n6409.dat";
    private double strutSpacing = 0.1;

    public void getGcode(BufferedWriter bufferedWriter) throws IOException {
        List<WingSegment> wingSegments = new ArrayList<WingSegment>();
        wingSegments.add(new WingSegment(50, 110));
        wingSegments.add(new WingSegment(100, 40));
        wingSegments.add(new WingSegment(108, 20));
        wingSegments.add(new WingSegment(110, 10));
        orientation = Gcode.ModelOrientation.vertical;
        addGcode(bufferedWriter, "start.gcode");
        writeInformativeHeader(bufferedWriter, wingSegments);
        final List<double[]> aerofoilData = getAerofoilData();
        final List<double[]> aerofoilStruts = getStruts(aerofoilData);
        final List<double[]> integratedStruts = getIntegratedStruts(aerofoilData);
//        List<List<double[]>> connectorData = getConnectorData();
        WingSegment previous = new WingSegment(0, wingSegments.get(0).targetChord);
        writeAnchor(bufferedWriter, previous.targetChord, previous.targetChord / 2);
        double maxZ = wingSegments.get(wingSegments.size() - 1).targetHeight;
        for (WingSegment current : wingSegments) {
            while (currentZ < current.targetHeight) {
                final double calculatedChord = calculateChord(previous, current, currentZ);
                writeLayer(bufferedWriter, integratedStruts, calculatedChord, false);
                setNextLayer(bufferedWriter);
                writeLayer(bufferedWriter, aerofoilStruts, calculatedChord, false);
                writeLayer(bufferedWriter, aerofoilData, calculatedChord, false);
                writePercentDone(bufferedWriter, maxZ, currentZ);
                extrudeSpeed = extrudeSpeedMax; // once the first layer is done we can increase the extrusion speed
                setNextLayer(bufferedWriter);
            }
            previous = current;
        }
//        writeComplexLayer(bufferedWriter, connectorData);
        addGcode(bufferedWriter, "end.gcode");
        bufferedWriter.close();
    }

    protected double calculateChord(WingSegment previous, WingSegment current, double currentLayer) {
        final double chordDifference = current.targetChord - previous.targetChord;
        final double totalHeight = current.targetHeight - previous.targetHeight;
        final double currentHeight = currentLayer - previous.targetHeight;
        final double fraction = currentHeight / totalHeight;
        return previous.targetChord + chordDifference * fraction;
    }

    private List<double[]> getAerofoilData() {
        List<double[]> pointsList = new ArrayList<double[]>();
        final InputStream aerofoilData = GcodeWing.this.getClass().getResourceAsStream(aerofoilDataFile);
        Scanner scanner = new Scanner(aerofoilData);
        while (scanner.hasNext()) {
            while (!scanner.hasNextDouble()) {
                scanner.next();
            }
            pointsList.add(new double[]{scanner.nextDouble(), scanner.nextDouble()});
        }
        return pointsList;
    }

    private List<double[]> getIntegratedStruts(List<double[]> pointsList) {
        List<double[]> pointsStrutsList = new ArrayList<double[]>();
        int startIndex = 0;
        int endIndex = pointsList.size() - 1;
        // add the initial part of the trailing edge
        while (pointsList.get(startIndex)[0] > 1 - strutSpacing) {
            pointsStrutsList.add(0, pointsList.get(startIndex));
            startIndex++;
        }
        boolean startEnd = true;
        // add the zigzags
        for (double currentPosition = 1 - strutSpacing * 2; currentPosition > strutSpacing; currentPosition -= strutSpacing) {
            if (startEnd) {
                while (pointsList.get(endIndex)[0] > currentPosition) {
                    pointsStrutsList.add(pointsList.get(endIndex - 1));
                    endIndex--;
                }
                endIndex++;
            } else {
                startIndex--;
                while (pointsList.get(startIndex)[0] > currentPosition) {
                    pointsStrutsList.add(pointsList.get(startIndex));
                    startIndex++;
                }
            }
            startEnd = !startEnd;
        }
        endIndex--;
        // add the final section at the leading edge
        while (startIndex < endIndex + 2) {
            pointsStrutsList.add(pointsList.get(endIndex));
            endIndex--;
        }
        return pointsStrutsList;
    }

    private List<double[]> getStruts(List<double[]> pointsList) {
        List<double[]> strutsList = new ArrayList<double[]>();
//        pointsStrutsList.addAll(pointsList);
        int startIndex = 0;
        int endIndex = pointsList.size() - 1;
        boolean startEnd = false;
        for (double currentPosition = 1 - strutSpacing; currentPosition > strutSpacing; currentPosition -= strutSpacing) {
            while (pointsList.get(startIndex)[0] > currentPosition) {
                startIndex++;
            }
            while (pointsList.get(endIndex)[0] > currentPosition) {
                endIndex--;
            }
            if (startEnd) {
                strutsList.add(0, pointsList.get(endIndex));
            } else {
                strutsList.add(0, pointsList.get(startIndex - 1));
            }
            startEnd = !startEnd;
        }
        return strutsList;
    }

    private List<List<double[]>> getConnectorData() {
        List<List<double[]>> complexData = new ArrayList<List<double[]>>();
        return complexData;
    }

    private void writeInformativeHeader(BufferedWriter bufferedWriter, List<WingSegment> wingSegments) throws IOException {
        bufferedWriter.write("; Aerofoil gcode produced by scratchbuiltcalculator: https://github.com/PeterWithers/ScratchBuiltCalculator\r\n");
        bufferedWriter.write("; Using aerofoil data file: " + aerofoilDataFile + "\r\n");
        for (WingSegment segment : wingSegments) {
            bufferedWriter.write("; segment chord length: " + segment.targetChord + "\r\n");
            bufferedWriter.write("; segment span length: " + segment.targetHeight + "\r\n");
        }
        bufferedWriter.write("; orientation: " + orientation.name() + "\r\n");
    }
}
