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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created on : Aug 18, 2013, 9:40 AM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgText {

    private double x;
    private double y;
    private String text;
    private Colour colour;

    public SvgText() {
    }

    public SvgText(double x, double y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        colour = new Colour(0x000000);
    }

    public SvgText(double x, double y, String text, Colour colour) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.colour = colour;
    }

    final public String getFormattedColour(Colour colour) {
        final String hexString = Integer.toHexString(colour.getRGB());
        final String fillHexString = "#" + hexString.substring(2, hexString.length()).toLowerCase();
        return fillHexString;
    }

    @XmlValue
    public String getText() {
        return text;
    }

    @XmlAttribute(name = "style")
    public String getStyle() {
        return "fill:" + getFormattedColour(colour) + ";font-size:10px;";
    }

    @XmlAttribute(name = "x")
    public double getX() {
        return x;
    }

    @XmlAttribute(name = "y")
    public double getY() {
        return y;
    }
}
