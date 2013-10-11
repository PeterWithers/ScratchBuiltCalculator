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

import com.bambooradical.scratchbuilt.data.TrainerData;
import com.bambooradical.scratchbuilt.data.WingSegment;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created on : Oct 07, 2013, 15:39 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class GcodeWingTest {

    /**
     * Test of calculateChord method, of class GcodeWing.
     */
    @Test
    public void testCalculateChord() {
        System.out.println("calculateChord");
        WingSegment previous = new WingSegment(50, 100);
        WingSegment current = new WingSegment(100, 50);
        GcodeWing instance = new GcodeWing(new TrainerData());
        assertEquals(75, instance.calculateChord(previous, current, 75), 0.0);
        assertEquals(100, instance.calculateChord(previous, current, 50), 0.0);
        assertEquals(50, instance.calculateChord(previous, current, 100), 0.0);
    }

    /**
     * Test of getGcode method, of class GcodeWing.
     */
    @Test
    public void testGetGcode() throws Exception {
        GcodeWing instance = new GcodeWing(new TrainerData());

        instance.getGcode(new BufferedWriter(new StringWriter()) {
            @Override
            public void write(char[] cbuf) throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void write(String str) throws IOException {
                System.out.println(str);
                if (str.contains("Z-") && !str.startsWith("G92")) {
                    fail("Printing below the platform: " + str);
                }
                if (str.contains("NaN")) {
                    fail("Found NaN: " + str);
                }
            }

            @Override
            public void write(int b) throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void write(char[] cbuf, int off, int len) throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void write(String s, int off, int len) throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
}