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

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgFuselage extends SvgGroup {

    private double length;
    private double startHeight;
    private double endHeight;
    protected Colour colour;

    public SvgFuselage() {
    }

    public SvgFuselage(ModelData modelData, double x, double y, String id, double length, double startHeight, double endHeight, Colour colour) {
        super(modelData, x, y, id);
        this.length = length;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        this.colour = colour;
    }

    private SvgPolyline getPolyline(double offsetX, double offsetY, double start, double end) {
        final SvgPolyline svgPolyline = new SvgPolyline(modelData, x + offsetX, y + offsetY, colour);
        double startOffset;
        double endOffset;
        if (start > end) {
            startOffset = 0;
            endOffset = (start - end) / 2.0;
        } else {
            startOffset = (end - start) / 2.0;
            endOffset = 0;
        }
        svgPolyline.addPoint(0, startOffset);
        svgPolyline.addPoint(0, start + startOffset);
        svgPolyline.addPoint(length, end + endOffset);
        svgPolyline.addPoint(length, endOffset);
        svgPolyline.addPoint(0, startOffset);
        return svgPolyline;
    }

    @Override
    public SvgPolyline[] getPolylines() {
        final double averageHeight = (startHeight + endHeight) / 2;
        return new SvgPolyline[]{getPolyline(0, 0, startHeight, endHeight),
            getPolyline(0, averageHeight, endHeight, startHeight),
            getPolyline(0, averageHeight * 2, startHeight, endHeight),
            getPolyline(0, averageHeight * 3, endHeight, startHeight)};
    }
}
