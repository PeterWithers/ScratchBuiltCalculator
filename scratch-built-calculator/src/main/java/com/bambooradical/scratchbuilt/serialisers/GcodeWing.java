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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    final private double targetHeight = 40.0;
    final private double targetChord = 80.0;
    private double currentX = 0;
    private double currentY = 0;
    private double currentZ = 0;
    private double currentA = 0;
    private double currentPercent = 0;
    private int primeSpeed = 1500;
    private int travelSpeed = 3000;
    private int extrudeSpeed = 1800;
    private ModelOrientation orientation;

    private enum ModelOrientation {

        horizontal,
        vertical
    }

    public GcodeWing(ModelData modelData) {
        this.modelData = modelData;
    }

    public void getGcode(OutputStream output) throws IOException {
        orientation = ModelOrientation.vertical;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output));
        writeInformativeHeader(bufferedWriter);
        selectExtruder(bufferedWriter);
        List<double[]> aerofoilData = getAerofoilData();
        while (currentZ < targetHeight) {
            setNextLayer(bufferedWriter);
            writeLayer(bufferedWriter, aerofoilData);
            writePercentDone(bufferedWriter, targetHeight, currentZ);
        }
        bufferedWriter.close();
    }

    private List<double[]> getAerofoilData() {
        List pointsList = new ArrayList<double[]>();
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

    private void writeInformativeHeader(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("; Aerofoil gcode produced by scratchbuiltcalculator: https://github.com/PeterWithers/ScratchBuiltCalculator\r\n");
        bufferedWriter.write("; Using aerofoil data file: " + aerofoilDataFile + "\r\n");
        bufferedWriter.write("; chord length: " + targetChord + "\r\n");
        bufferedWriter.write("; span length: " + targetHeight + "\r\n");
    }

    private void selectExtruder(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("M135 T0; select the extruder\r\n");
    }

    private void writePercentDone(BufferedWriter bufferedWriter, double linesToDo, double linesDone) throws IOException {
        final double percentDone = linesDone / linesToDo * 100;
        if (currentPercent + 1 < percentDone) {
            bufferedWriter.write(String.format("M73 P%.0f\r\n", percentDone));
            currentPercent = percentDone;
        }
    }

    private void setNextLayer(BufferedWriter bufferedWriter) throws IOException {
        currentZ += layerHeight;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f; next layer\r\n", currentX, currentY, currentZ));
    }

    private void writeLayer(BufferedWriter bufferedWriter, List<double[]> aerofoilData) throws IOException {
        final int xAxisElement;
        final int yAxisElement;
        switch (orientation) {
            case horizontal:
                xAxisElement = 0;
                yAxisElement = 1;
                break;
            default:
                xAxisElement = 1;
                yAxisElement = 0;
                break;
        }

        currentX = aerofoilData.get(0)[xAxisElement] * targetChord - targetChord / 2;
        currentY = aerofoilData.get(0)[yAxisElement] * targetChord - targetChord / 2;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d; move\r\n", currentX, currentY, currentZ, travelSpeed));
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; prime\r\n", currentX, currentY, currentZ, primeSpeed, currentA));
        for (double[] dataElement : aerofoilData) {
            final double nextX = dataElement[xAxisElement] * targetChord - targetChord / 2;
            final double nextY = dataElement[yAxisElement] * targetChord - targetChord / 2;
            final double filamentTravel = calculateFilamentUsed(currentX, currentY, nextX, nextY);
            currentA += filamentTravel;
            currentX = nextX;
            currentY = nextY;
            bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; data point\r\n", currentX, currentY, currentZ, extrudeSpeed, currentA));
        }
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; deprime\r\n", currentX, currentY, currentZ, primeSpeed, currentA - 1));
    }

    private double calculateFilamentUsed(double startX, double startY, double endX, double endY) {
        double lengthA = (startY - endY);
        double lengthB = (startX - endX);
        return Math.sqrt(lengthA * lengthA + lengthB * lengthB) / 100;
    }
}
