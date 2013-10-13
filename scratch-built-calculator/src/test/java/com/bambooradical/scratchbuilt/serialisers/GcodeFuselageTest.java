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
import org.junit.Test;

/**
 * Created on : Oct 13, 2013, 14:30 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class GcodeFuselageTest {

    public GcodeFuselageTest() {
    }

    /**
     * Test of getGcode method, of class GcodeFuselage.
     */
    @Test
    public void testGetGcode() throws Exception {
        System.out.println("getGcode");
        GcodeFuselage instance = new GcodeFuselage(new TrainerData());
        instance.getGcode(new GcodeValidator());
    }
}