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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgFuselageTest {

    public SvgFuselageTest() {
    }

    /**
     * Test of getOptimalOffset method, of class SvgFuselage.
     */
    @Test
    public void testGetOptimalOffset() {
        System.out.println("getOptimalOffset");
        SvgFuselage instance = new SvgFuselage();
        double result = instance.getOptimalOffset(5, 10, 10, 5);
        assertEquals((10 + 5) / 2.0, result, 0.0);
        double result1 = instance.getOptimalOffset(10, 5, 10, 5);
        assertEquals((10 + 10) / 2.0, result1, 0.0);
        double result2 = instance.getOptimalOffset(5, 10, 5, 10);
        assertEquals((10 + 10) / 2.0, result2, 0.0);
        double result3 = instance.getOptimalOffset(10, 5, 5, 10);
        assertEquals((10 + 5) / 2.0, result3, 0.0);
        double result4 = instance.getOptimalOffset(15, 5, 5, 10);
        assertEquals(15 - 2.5, result4, 0.0);
        double result5 = instance.getOptimalOffset(5, 15, 10, 5);
        assertEquals(15 - 2.5, result5, 0.0);
        double result6 = instance.getOptimalOffset(10, 5, 5, 15);
        assertEquals(10 - 2.5, result6, 0.0);
    }
}