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
package com.bambooradical.scratchbuilt.data;

/**
 * Created on : Jun 26, 2013, 8:29:05 AM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class Colour {

    static final public Colour BLACK = new Colour(0x000000);
    static final public Colour WHITE = new Colour(0xffffff);
    static final public Colour GREY = new Colour(0x808080);
    static final public Colour LIGHT_RED = new Colour(0x800000);
    static final public Colour LIGHT_GREEN = new Colour(0x008000);
    static final public Colour LIGHT_BLUE = new Colour(0x000080);
    final private int colourValue;

    public Colour(int colourValue) {
        this.colourValue = colourValue | 0xff000000;
    }

    public int getRed() {
        return (colourValue >> 16) & 0xFF;
    }

    public int getGreen() {
        return (colourValue >> 8) & 0xFF;
    }

    public int getBlue() {
        return colourValue & 0xFF;
    }

    public int getRGB() {
        return colourValue;
    }
}
