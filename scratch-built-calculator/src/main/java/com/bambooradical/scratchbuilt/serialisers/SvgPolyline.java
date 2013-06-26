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
import com.bambooradical.scratchbuilt.data.ModelData;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgPolyline {

    protected ModelData modelData;
    private double x;
    private double y;
    private String points = "";
    private double maxX = 0;
    private double maxY = 0;
    private String style;

    public SvgPolyline() {
    }

    public SvgPolyline(ModelData modelData, double x, double y, Colour colour) {
        this.modelData = modelData;
        this.x = x;
        this.y = y;
        this.style = "fill:" + getFormattedColour(colour) + ";stroke:black;stroke-width:1";
    }

    final public String getFormattedColour(Colour colour) {
        final String hexString = Integer.toHexString(colour.getRGB());
        final String fillHexString = "#" + hexString.substring(2, hexString.length()).toLowerCase();
        return fillHexString;
    }

    private double adjustToScale(double inputValue) {
        return inputValue / 1.0;
    }

    protected String addPoint(double pointX, double pointY) {
        maxX = maxX < pointX ? pointX : maxX;
        maxX = maxX < pointY ? pointY : maxY;
        return points = points + adjustToScale(x + pointX) + "," + adjustToScale(y + pointY) + " ";
    }

    @XmlAttribute(name = "points")
    public String getPoints() {
        return points;
    }

    @XmlAttribute(name = "style")
    public String getStyle() {
        return style;
    }

    @XmlTransient
    public double getMaxPageX() {
        return maxX + x;
    }

    @XmlTransient
    public double getMaxPageY() {
        return maxY + y;
    }
}
