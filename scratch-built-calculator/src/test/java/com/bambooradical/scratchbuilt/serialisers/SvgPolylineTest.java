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
public class SvgPolylineTest {

    public SvgPolylineTest() {
    }

    /**
     * Test of getFormattedColour method, of class SvgPolyline.
     */
    @Test
    public void testGetFormattedColour() {
        System.out.println("getFormattedColour");
        SvgPolyline instance = new SvgPolyline();
        String result1 = instance.getFormattedColour(Colour.WHITE);
        assertEquals("#ffffff", result1);
        String result2 = instance.getFormattedColour(Colour.BLACK);
        assertEquals("#000000", result2);
    }
}