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
        WingSegment previous = new WingSegment(50, 100, 0, 0);
        WingSegment current = new WingSegment(100, 50, 0, 0);
        GcodeWing instance = new GcodeWing(new TrainerData());
        assertEquals(75, instance.interpolateValue(previous, previous.targetChord, current, current.targetChord, 75), 0.0);
        assertEquals(100, instance.interpolateValue(previous, previous.targetChord, current, current.targetChord, 50), 0.0);
        assertEquals(50, instance.interpolateValue(previous, previous.targetChord, current, current.targetChord, 100), 0.0);
    }

    /**
     * Test of getGcode method, of class GcodeWing.
     */
    @Test
    public void testGetGcode() throws Exception {
        GcodeWing instance = new GcodeWing(new TrainerData());
        instance.getGcode(new GcodeValidator());
    }
}