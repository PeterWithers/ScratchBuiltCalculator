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
        writeAnchor(bufferedWriter, modelData.getStabiliserSpanTrailing(), modelData.getStabiliserHeightTrailing() * 2);

        while (currentZ < modelData.getFuselageLength()) {
            if (currentZ < modelData.getStabiliserChord()) {
                addTail(bufferedWriter);
            }
            final FuselageSection[] fuselageSections = modelData.getFuselageSections();
            for (int sectionIndex = fuselageSections.length - 1; sectionIndex >= 0; sectionIndex--) {
                FuselageSection fuselageSection = fuselageSections[sectionIndex];
                final double offsetX;
                final double offsetY;
                if (sectionIndex == fuselageSections.length - 1) {
                    offsetX = 0;
                    offsetY = 0;
                } else {
                    offsetX = 25;
                    offsetY = (sectionIndex % 2) * 50 - 25;
                }
//                if(sectionIndex==0)
                addFuselage(bufferedWriter, fuselageSection, currentZ, offsetX, offsetY);
            }
            setNextLayer(bufferedWriter);
            writePercentDone(bufferedWriter, modelData.getFuselageLength(), currentZ);
        }
        addGcode(bufferedWriter, "end.gcode");
        bufferedWriter.close();
    }

    protected void addFuselage(BufferedWriter bufferedWriter, FuselageSection fuselageSection, double currentLayer, double offsetX, double offsetY) throws IOException {
        final double widthDifference = fuselageSection.getEndWidth() - fuselageSection.getStartWidth();
        final double heightDifference = fuselageSection.getEndHeight() - fuselageSection.getStartHeight();
        final double totalHeight = fuselageSection.getLength();
        final double fraction = currentLayer / totalHeight;
        final double currentWidth = fuselageSection.getEndWidth() - widthDifference * fraction;
        final double currentHeight = fuselageSection.getEndHeight() - heightDifference * fraction;
        double wallThickness = 0.9;
        if (currentLayer < fuselageSection.getLength()) {
            moveTo(currentHeight / 2 + offsetX, currentWidth / 2 + offsetY, bufferedWriter);
            extrudeTo(currentHeight / 2 + offsetX, -currentWidth / 2 + offsetY, bufferedWriter);
            extrudeTo(-currentHeight / 2 + offsetX, -currentWidth / 2 + offsetY, bufferedWriter);
            extrudeTo(-currentHeight / 2 + offsetX, currentWidth / 2 + offsetY, bufferedWriter);
            extrudeTo(currentHeight / 2 + offsetX, currentWidth / 2 + offsetY, bufferedWriter);
            if (currentLayer < 3) {
                // add the female part of the connector              
                // first part
                moveTo(currentHeight / 2 + offsetX - wallThickness, currentWidth / 2 + offsetY, bufferedWriter);
                extrudeTo(offsetX, wallThickness + offsetY, bufferedWriter);
                extrudeTo(-currentHeight / 2 + offsetX + wallThickness, currentWidth / 2 + offsetY, bufferedWriter);
                // second part
                moveTo(-currentHeight / 2 + offsetX + wallThickness, -currentWidth / 2 + offsetY, bufferedWriter);
                extrudeTo(offsetX, offsetY - wallThickness, bufferedWriter);
                extrudeTo(currentHeight / 2 + offsetX - wallThickness, -currentWidth / 2 + offsetY, bufferedWriter);
                // third part
                moveTo(-currentHeight / 2 + offsetX, -currentWidth / 2 + offsetY + wallThickness, bufferedWriter);
                extrudeTo(offsetX - wallThickness, offsetY, bufferedWriter);
                extrudeTo(-currentHeight / 2 + offsetX, currentWidth / 2 + offsetY - wallThickness, bufferedWriter);
                // forth part
                moveTo(currentHeight / 2 + offsetX, -currentWidth / 2 + offsetY + wallThickness, bufferedWriter);
                extrudeTo(offsetX + wallThickness, offsetY, bufferedWriter);
                extrudeTo(currentHeight / 2 + offsetX, currentWidth / 2 + offsetY - wallThickness, bufferedWriter);
            } else {
                // add the standard internal strut
                extrudeTo(-currentHeight / 2 + offsetX, -currentWidth / 2 + offsetY, bufferedWriter);
            }
        } else if (currentLayer < fuselageSection.getLength() + 2) {
            // add the male part of the connector
            moveTo(currentHeight / 2 + offsetX - wallThickness, currentWidth / 2 + offsetY - wallThickness, bufferedWriter);
            extrudeTo(-currentHeight / 2 + offsetX + wallThickness, -currentWidth / 2 + offsetY + wallThickness, bufferedWriter);
            moveTo(currentHeight / 2 + offsetX - wallThickness, -currentWidth / 2 + offsetY + wallThickness, bufferedWriter);
            extrudeTo(-currentHeight / 2 + offsetX + wallThickness, currentWidth / 2 + offsetY - wallThickness, bufferedWriter);
        }
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
