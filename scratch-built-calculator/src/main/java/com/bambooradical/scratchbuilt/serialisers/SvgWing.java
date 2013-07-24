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
import java.util.ArrayList;
import java.util.List;

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
    protected double getHeight() {
        double totalWidth = 0;
        for (double[] lengthsAngles : modelData.getWingType().getLengths()) {
            final double currentlength = lengthsAngles[0] * modelData.getChordLength();
            totalWidth += currentlength;
        }
        return totalWidth;
    }

    @Override
    public List<SvgPolyline> getPolylines() {
        final ArrayList<SvgPolyline> returnList = new ArrayList<SvgPolyline>();
//        final SvgPolyline wingPolyline = new SvgPolyline(modelData, x, y, modelData.getMainWingColour());
//        wingPolyline.addPoint(0, 0);
//        wingPolyline.addPoint(modelData.getWingSpan(), 0);
        double totalWidth = 0;
        for (double[] lengthsAngles : modelData.getWingType().getLengths()) {
            final double currentlength = lengthsAngles[0] * modelData.getChordLength();
            final SvgPolyline foldPolyline = new SvgPolyline(modelData, x, y, modelData.getMainWingColour());
            foldPolyline.addPoint(0, totalWidth);
            foldPolyline.addPoint(modelData.getWingSpan(), totalWidth);
            foldPolyline.addPoint(modelData.getWingSpan(), totalWidth + currentlength);
            foldPolyline.addPoint(0, totalWidth + currentlength);
            foldPolyline.addPoint(0, totalWidth);
            totalWidth += currentlength;
            returnList.add(foldPolyline);
        }
//        wingPolyline.addPoint(modelData.getWingSpan(), totalWidth);
//        wingPolyline.addPoint(0, totalWidth);
//        wingPolyline.addPoint(0, 0);
//        returnList.add(wingPolyline);
        final SvgPolyline aileronLPolyline = new SvgPolyline(modelData, x, y, modelData.getElevatorColour());
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronEnd(), 0);
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronEnd(), modelData.getAileronChord());
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronStart(), modelData.getAileronChord());
        aileronLPolyline.addPoint(modelData.getWingLength() - modelData.getAileronStart(), 0);
        returnList.add(aileronLPolyline);
        final SvgPolyline aileronRPolyline = new SvgPolyline(modelData, x, y, modelData.getElevatorColour());
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronEnd()), 0);
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronEnd()), modelData.getAileronChord());
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronStart()), modelData.getAileronChord());
        aileronRPolyline.addPoint(modelData.getWingSpan() - (modelData.getWingLength() - modelData.getAileronStart()), 0);
        returnList.add(aileronRPolyline);
        return returnList;
    }
}
