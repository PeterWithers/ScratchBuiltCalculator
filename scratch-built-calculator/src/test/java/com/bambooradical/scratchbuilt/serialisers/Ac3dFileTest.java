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

import com.bambooradical.scratchbuilt.data.Colour;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class Ac3dFileTest {

    public Ac3dFileTest() {
    }

    /**
     * Test of getFormattedColour method, of class Ac3dFile.
     */
    @Test
    public void testGetFormattedColour() {
        System.out.println("getFormattedColour");
        Colour colour = null;
        Ac3dFile instance = new Ac3dFile(null);
        String expResult0 = "0.00 0.00 0.00";
        String result0 = instance.getFormattedColour(Colour.BLACK);
        assertEquals(expResult0, result0);
        String expResult1 = "1.00 1.00 1.00";
        String result1 = instance.getFormattedColour(Colour.WHITE);
        assertEquals(expResult1, result1);
        String expResult2 = "0.50 0.50 0.50";
        String result2 = instance.getFormattedColour(Colour.GREY);
        assertEquals(expResult2, result2);
    }
}