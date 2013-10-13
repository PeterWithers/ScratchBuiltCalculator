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
import java.util.List;
import java.util.Scanner;

/**
 * Created on : Oct 12, 2013, 20:36 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public abstract class Gcode {
    // all units are in millimeters

    final protected ModelData modelData;
    final private double layerHeight = 0.2;
    private double currentX = 0;
    private double currentY = 0;
    protected double currentZ = layerHeight;
    private double currentA = 0;
    private double currentPercent = 0;
    private int primeSpeed = 1500;
    private int travelSpeed = 3000;
    private int extrudeSpeedFirstLayer = 200;
    protected int extrudeSpeedMax = 1800;
    protected int extrudeSpeed = extrudeSpeedFirstLayer;
    protected ModelOrientation orientation;

    protected enum ModelOrientation {

        horizontal,
        vertical
    }

    public Gcode(ModelData modelData) {
        this.modelData = modelData;
    }

    public abstract void getGcode(BufferedWriter bufferedWriter) throws IOException;

    protected void addGcode(BufferedWriter bufferedWriter, String fileName) throws IOException {
        final String resourceName = "/GcodeStartEnd/" + fileName;
        final InputStream aerofoilData = Gcode.this.getClass().getResourceAsStream(resourceName);
        Scanner scanner = new Scanner(aerofoilData);
        while (scanner.hasNext()) {
            bufferedWriter.write(scanner.nextLine() + "\r\n");
        }
    }

    protected void writePercentDone(BufferedWriter bufferedWriter, double linesToDo, double linesDone) throws IOException {
        final double percentDone = linesDone / linesToDo * 100;
        if (currentPercent + 1 < percentDone) {
            bufferedWriter.write(String.format("M73 P%.0f\r\n", percentDone));
            currentPercent = percentDone;
        }
    }

    protected void writeAnchor(BufferedWriter bufferedWriter, double targetChord, double targetHeight) throws IOException {
        final double anchorLongSide = targetChord / 2;
        final double anchorShortSide = targetHeight / 2;
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

    protected void setNextLayer(BufferedWriter bufferedWriter) throws IOException {
        currentZ += layerHeight;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f; next layer\r\n", currentX, currentY, currentZ));
    }

    protected void extrudeTo(final double nextX, final double nextY, BufferedWriter bufferedWriter) throws IOException {
        final double filamentTravel = calculateFilamentUsed(currentX, currentY, nextX, nextY);
        currentA += filamentTravel;
        currentX = nextX;
        currentY = nextY;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; extrude\r\n", currentX, currentY, currentZ, extrudeSpeed, currentA));
    }

    protected void moveTo(final double nextX, final double nextY, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; deprime\r\n", currentX, currentY, currentZ, primeSpeed, currentA - 1));
        currentX = nextX;
        currentY = nextY;
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d; move\r\n", currentX, currentY, currentZ, travelSpeed));
        bufferedWriter.write(String.format("G1 X%.3f Y%.3f Z%.3f F%d A%.5f; prime\r\n", currentX, currentY, currentZ, primeSpeed, currentA));
    }

    protected void writeComplexLayer(BufferedWriter bufferedWriter, List<List<double[]>> complexData, double targetChord) throws IOException {
        for (List<double[]> segment : complexData) {
            writeLayer(bufferedWriter, segment, targetChord, true);
        }
    }

    protected void writeLayer(BufferedWriter bufferedWriter, List<double[]> aerofoilData, double targetChord, boolean deprime) throws IOException {
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
            extrudeTo(nextX, nextY, bufferedWriter);
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
