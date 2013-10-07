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
import java.util.Scanner;

/**
 * Created on : Oct 07, 2013, 15:39 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class GcodeWing {

    // all units are in millimeters
    final private ModelData modelData;
    final private double layerHeight = 0.2;
    final private double targetHeight = 40.0;
    final private double targetChord = 80.0;
    private double currentX = 0;
    private double currentY = 0;
    private double currentZ = 0;
    private double currentPercent = 0;

    public GcodeWing(ModelData modelData) {
        this.modelData = modelData;
    }

    public void getGcode(OutputStream output) throws IOException {
        final InputStream aerofoilData = GcodeWing.this.getClass().getResourceAsStream("/AerofoilData/n6409.dat");
        Scanner scanner = new Scanner(aerofoilData);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(output));
        bufferedWriter.write("start of file");
        while (scanner.hasNext()) {
            bufferedWriter.write(scanner.nextLine());
        }
        while (currentZ < targetHeight) {
            setNextLayer(bufferedWriter);
            writePercentDone(bufferedWriter, targetHeight, currentZ);
        }
        bufferedWriter.close();
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
}
