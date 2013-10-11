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
public class GcodeWing {

    // all units are in millimeters
    final private ModelData modelData;
    final private String aerofoilDataFile = "/AerofoilData/n6409.dat";
    final private double layerHeight = 0.2;
    private double strutSpacing = 0.1;
    private double currentX = 0;
    private double currentY = 0;
    private double currentZ = layerHeight;
    private double currentA = 0;
    private double currentPercent = 0;
    private int primeSpeed = 1500;
    private int travelSpeed = 3000;
    private int extrudeSpeedFirstLayer = 200;
    private int extrudeSpeedMax = 1800;
    private int extrudeSpeed = extrudeSpeedFirstLayer;
    private ModelOrientation orientation;

    private enum ModelOrientation {

        horizontal,
        vertical
    }

    public GcodeWing(ModelData modelData) {
        this.modelData = modelData;
    }

    public void getGcode(BufferedWriter bufferedWriter) throws IOException {
        List<WingSegment> wingSegments = new ArrayList<WingSegment>();
        wingSegments.add(new WingSegment(10, 50));
        wingSegments.add(new WingSegment(20, 40));
        wingSegments.add(new WingSegment(28, 20));
        wingSegments.add(new WingSegment(30, 10));
        orientation = ModelOrientation.vertical;
        addGcode(bufferedWriter, "start.gcode");
        writeInformativeHeader(bufferedWriter, wingSegments);
        final List<double[]> aerofoilData = getAerofoilData();
        final List<double[]> aerofoilStruts = getStruts(aerofoilData);
        final List<double[]> integratedStruts = getIntegratedStruts(aerofoilData);
//        List<List<double[]>> connectorData = getConnectorData();
        WingSegment previous = new WingSegment(0, wingSegments.get(0).targetChord);
        writeAnchor(bufferedWriter, previous.targetChord);
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

    private void addGcode(BufferedWriter bufferedWriter, String fileName) throws IOException {
        final String resourceName = "/GcodeStartEnd/" + fileName;
        final InputStream aerofoilData = GcodeWing.this.getClass().getResourceAsStream(resourceName);
        Scanner scanner = new Scanner(aerofoilData);
        while (scanner.hasNext()) {
            bufferedWriter.write(scanner.nextLine() + "\r\n");
        }
    }

    private void writePercentDone(BufferedWriter bufferedWriter, double linesToDo, double linesDone) throws IOException {
        final double percentDone = linesDone / linesToDo * 100;
        if (currentPercent + 1 < percentDone) {
            bufferedWriter.write(String.format("M73 P%.0f\r\n", percentDone));
            currentPercent = percentDone;
        }
    }

    private void writeAnchor(BufferedWriter bufferedWriter, double targetChord) throws IOException {
        final double anchorLongSide = targetChord / 2;
        final double anchorShortSide = targetChord / 4;
        for (double[] anchorPoint : new double[][]{{-anchorLongSide, -anchorShortSide, anchorLongSide, -anchorShortSide}, {-anchorLongSide, anchorShortSide, anchorLongSide, anchorShortSide}, {anchorLongSide, anchorShortSide, anchorLongSide, -anchorShortSide}, {-anchorLongSide, anchorShortSide, -anchorLongSide, -anchorShortSide},}) {
            final double nextX;
            final double nextY;
            switch (orientation) {
                case vertical:
                    currentX = anchorPoint[1];
                    currentY = anchorPoint[0];
                    nextX = anchorPoint[3];
                    nextY = anchorPoint[2];
                    break;
                default:
                    currentX = anchorPoint[0];
                    currentY = anchorPoint[1];
                    nextX = anchorPoint[2];
                    nextY = anchorPoint[3];
            }
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d; move\r\n", currentX, currentY, currentZ, travelSpeed));
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; prime\r\n", currentX, currentY, currentZ, primeSpeed, currentA));
            final double filamentTravel = calculateFilamentUsed(currentX, currentY, nextX, nextY);
            currentA += filamentTravel * 4; // use double the filament for the anchor
            currentX = nextX;
            currentY = nextY;
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; anchor\r\n", currentX, currentY, currentZ, extrudeSpeedMax, currentA));
        }
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; deprime\r\n", currentX, currentY, currentZ, primeSpeed, currentA - 1));
    }

    private void setNextLayer(BufferedWriter bufferedWriter) throws IOException {
        currentZ += layerHeight;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f; next layer\r\n", currentX, currentY, currentZ));
    }

    private void writeComplexLayer(BufferedWriter bufferedWriter, List<List<double[]>> complexData, double targetChord) throws IOException {
        for (List<double[]> segment : complexData) {
            writeLayer(bufferedWriter, segment, targetChord, true);
        }
    }

    private void writeLayer(BufferedWriter bufferedWriter, List<double[]> aerofoilData, double targetChord, boolean deprime) throws IOException {
        final int xAxisElement;
        final int yAxisElement;
        final double xOffset;
        final double yOffset;
        switch (orientation) {
            case horizontal:
                xAxisElement = 0;
                yAxisElement = 1;
                xOffset = targetChord / 2;
                yOffset = 0;
                break;
            default:
                xAxisElement = 1;
                yAxisElement = 0;
                xOffset = 0;
                yOffset = targetChord / 2;
                break;
        }

        currentX = aerofoilData.get(0)[xAxisElement] * targetChord - xOffset;
        currentY = aerofoilData.get(0)[yAxisElement] * targetChord - yOffset;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d; move\r\n", currentX, currentY, currentZ, travelSpeed));
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; prime\r\n", currentX, currentY, currentZ, primeSpeed, currentA));
        for (double[] dataElement : aerofoilData) {
            final double nextX = dataElement[xAxisElement] * targetChord - xOffset;
            final double nextY = dataElement[yAxisElement] * targetChord - yOffset;
            final double filamentTravel = calculateFilamentUsed(currentX, currentY, nextX, nextY);
            currentA += filamentTravel;
            currentX = nextX;
            currentY = nextY;
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; data point\r\n", currentX, currentY, currentZ, extrudeSpeed, currentA));
        }
        if (deprime) {
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; deprime\r\n", currentX, currentY, currentZ, primeSpeed, currentA - 1));
        }
    }

    private double calculateFilamentUsed(double startX, double startY, double endX, double endY) {
        double lengthA = (startY - endY);
        double lengthB = (startX - endX);
        return Math.sqrt(lengthA * lengthA + lengthB * lengthB) / 30.0;
    }
}
