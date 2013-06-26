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
 * Created on : Jun 26, 2013, 19:29:05 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class FuselageSection {

    private int startWidth;
    private int endWidth;
    private int startHeight;
    private int endHeight;
    private int start;
    private int length;
    private Colour colour;
    private boolean requiresTop;
    private String label;

    public FuselageSection(int startWidth, int startHeight, int endWidth, int endHeight, int start, int length, Colour colour, boolean requiresTop, String label) {
        this.startWidth = startWidth;
        this.endWidth = endWidth;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        this.start = start;
        this.length = length;
        this.colour = colour;
        this.requiresTop = requiresTop;
        this.label = label;
    }

    public int getStartWidth() {
        return startWidth;
    }

    public int getEndWidth() {
        return endWidth;
    }

    public int getStartHeight() {
        return startHeight;
    }

    public int getEndHeight() {
        return endHeight;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return start + length;
    }

    public int getLength() {
        return length;
    }

    public Colour getColour() {
        return colour;
    }

    public boolean isRequiresTop() {
        return requiresTop;
    }

    public String getLabel() {
        return label;
    }
}
