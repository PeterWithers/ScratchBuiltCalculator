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

import com.bambooradical.scratchbuilt.data.FuselageSection;
import com.bambooradical.scratchbuilt.data.ModelData;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on : Oct 12, 2013, 20:35 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class GcodeFuselage extends Gcode {

    public GcodeFuselage(ModelData modelData) {
        super(modelData);
    }

    @Override
    public void getGcode(BufferedWriter bufferedWriter) throws IOException {
        orientation = Gcode.ModelOrientation.vertical;
        addGcode(bufferedWriter, "start.gcode");
        writeInformativeHeader(bufferedWriter);
        final List<double[]> fuselageData = getFuselageData();
//        final List<double[]> aerofoilStruts = getStruts(aerofoilData);
//        final List<double[]> integratedStruts = getIntegratedStruts(aerofoilData);
////        List<List<double[]>> connectorData = getConnectorData();
//        WingSegment previous = new WingSegment(0, wingSegments.get(0).targetChord);
//        writeAnchor(bufferedWriter, previous.targetChord);
//        double maxZ = wingSegments.get(wingSegments.size() - 1).targetHeight;
        for (FuselageSection fuselageSection : modelData.getFuselageSections()) {
            while (currentZ < fuselageSection.getEnd()) {
                if (currentZ < modelData.getStabiliserChord()) {
                    addTail(bufferedWriter);
                }
                final double calculatedChord = calculateChord(fuselageSection, currentZ);
                writeLayer(bufferedWriter, fuselageData, calculatedChord, false);
                setNextLayer(bufferedWriter);
//                writeLayer(bufferedWriter, aerofoilStruts, calculatedChord, false);
//                writeLayer(bufferedWriter, aerofoilData, calculatedChord, false);
//                writePercentDone(bufferedWriter, maxZ, currentZ);
//                extrudeSpeed = extrudeSpeedMax; // once the first layer is done we can increase the extrusion speed
//                setNextLayer(bufferedWriter);
            }
//            previous = current;
        }
//        writeComplexLayer(bufferedWriter, connectorData);
        addGcode(bufferedWriter, "end.gcode");
        bufferedWriter.close();
    }

    protected double calculateChord(FuselageSection fuselageSection, double currentLayer) {
        // todo: change this to get width and height
        final double chordDifference = fuselageSection.getEndWidth() - fuselageSection.getStartWidth();
        final double totalHeight = fuselageSection.getLength();
        final double currentHeight = currentLayer - fuselageSection.getStart();
        final double fraction = currentHeight / totalHeight;
        return fuselageSection.getStartWidth() + chordDifference * fraction;
    }

    private List<double[]> getFuselageData() {
        List<double[]> pointsList = new ArrayList<double[]>();
        pointsList.add(new double[]{1, 1});
        pointsList.add(new double[]{1, -1});
        pointsList.add(new double[]{-1, -1});
        pointsList.add(new double[]{-1, 1});
        pointsList.add(new double[]{1, 1});
        return pointsList;
    }

    private void addTail(BufferedWriter bufferedWriter) throws IOException {
        final int stabiliserChord = modelData.getStabiliserChord();
        final int heightTrailing = modelData.getStabiliserHeightTrailing();
        final int heightLeading = modelData.getStabiliserHeightLeading();
        final int spanLeading = modelData.getStabiliserSpanLeading();
        final int spanTrailing = modelData.getStabiliserSpanTrailing();
        final double verticalDifference = heightLeading - heightTrailing;
        final double horizontalDifference = spanLeading - spanTrailing;
        final double fraction = currentZ / stabiliserChord;
        final double verticalChord = heightTrailing + verticalDifference * fraction;
        final double horizontalChord = (spanTrailing + horizontalDifference * fraction) / 2;
        final double finWidth = 0.3;
        moveTo(finWidth, finWidth, bufferedWriter);
        extrudeTo(verticalChord, finWidth, bufferedWriter);
        extrudeTo(verticalChord, -finWidth, bufferedWriter);
        extrudeTo(finWidth, -finWidth, bufferedWriter);
        extrudeTo(finWidth, -horizontalChord, bufferedWriter);
        extrudeTo(-finWidth, -horizontalChord, bufferedWriter);
        extrudeTo(-finWidth, horizontalChord, bufferedWriter);
        extrudeTo(finWidth, horizontalChord, bufferedWriter);
        extrudeTo(finWidth, finWidth, bufferedWriter);
    }

    private void writeInformativeHeader(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("; Fuselage gcode produced by scratchbuiltcalculator: https://github.com/PeterWithers/ScratchBuiltCalculator\r\n");

        bufferedWriter.write("; FuselageEndsDiameter: " + modelData.getFuselageEndsDiameter() + "\r\n");
        for (FuselageSection fuselageSection : modelData.getFuselageSections()) {
            bufferedWriter.write("; Start: " + fuselageSection.getStart() + "\r\n");
            bufferedWriter.write("; StartHeight: " + fuselageSection.getStartHeight() + "\r\n");
            bufferedWriter.write("; StartWidth: " + fuselageSection.getStartWidth() + "\r\n");
            bufferedWriter.write("; Length: " + fuselageSection.getLength() + "\r\n");
            bufferedWriter.write("; End: " + fuselageSection.getEnd() + "\r\n");
            bufferedWriter.write("; EndHeight: " + fuselageSection.getEndHeight() + "\r\n");
            bufferedWriter.write("; EndWidth: " + fuselageSection.getEndWidth() + "\r\n");
        }
        bufferedWriter.write("; StabiliserChord: " + modelData.getStabiliserChord() + "\r\n");
        bufferedWriter.write("; StabiliserHeightLeading: " + modelData.getStabiliserHeightLeading() + "\r\n");
        bufferedWriter.write("; StabiliserHeightTrailing: " + modelData.getStabiliserHeightTrailing() + "\r\n");
        bufferedWriter.write("; StabiliserSpanLeading: " + modelData.getStabiliserSpanLeading() + "\r\n");
        bufferedWriter.write("; StabiliserSpanTrailing: " + modelData.getStabiliserSpanTrailing() + "\r\n");
        bufferedWriter.write("; orientation: " + orientation.name() + "\r\n");
    }
}
