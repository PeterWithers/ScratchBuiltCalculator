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

import com.bambooradical.scratchbuilt.data.ModelData;

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgWing extends SvgGroup {

    public SvgWing() {
    }

    public SvgWing(ModelData modelData, double x, double y, String id) {
        super(modelData, x, y, id);
    }

    @Override
    public SvgPolyline[] getPolylines() {
        final SvgPolyline wingPolyline = new SvgPolyline(modelData, x, y);
        wingPolyline.addPoint(0, 0);
        wingPolyline.addPoint(modelData.getWingSpan(), 0);
        wingPolyline.addPoint(modelData.getWingSpan(), modelData.getChordLength());
        wingPolyline.addPoint(0, modelData.getChordLength());
        wingPolyline.addPoint(0, 0);
        final SvgPolyline aileronLPolyline = new SvgPolyline(modelData, x, y);
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronEnd(), 0);
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronEnd(), modelData.getAileronChord());
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronStart(), modelData.getAileronChord());
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronStart(), 0);
        final SvgPolyline aileronRPolyline = new SvgPolyline(modelData, x, y);
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronEnd()), 2);
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronEnd()), modelData.getAileronChord());
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronStart()), modelData.getAileronChord());
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronStart()), 0);
        return new SvgPolyline[]{wingPolyline, aileronLPolyline, aileronRPolyline};
    }
}
