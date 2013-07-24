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
 * Created on : July 24, 2013, 21:52
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgStabilisers extends SvgGroup {

    public SvgStabilisers() {
    }

    public SvgStabilisers(ModelData modelData, double x, double y, String id) {
        super(modelData, x, y, id);
    }

    @Override
    protected double getHeight() {
        return modelData.getStabiliserChord();
    }

    @Override
    protected double getWidth() {
        // todo: this offset will not always be correct
        return modelData.getStabiliserHeightLeading() + modelData.getStabiliserSpanTrailing();
    }

    @Override
    public List<SvgPolyline> getPolylines() {
        final ArrayList<SvgPolyline> returnList = new ArrayList<SvgPolyline>();
        final SvgPolyline verticalPolyline = new SvgPolyline(modelData, x, y, modelData.getHStabiliserColour());
//        double maxHeight = (modelData.getStabiliserHeightLeading() > modelData.getStabiliserHeightTrailing()) ? modelData.getStabiliserHeightLeading() : modelData.getStabiliserHeightTrailing();
        double horizontalStabiliserOffset = modelData.getStabiliserHeightLeading(); // todo: this offset will not always be correct
        verticalPolyline.addPoint(0, 0);
        verticalPolyline.addPoint(modelData.getStabiliserHeightLeading(), 0);
        verticalPolyline.addPoint(modelData.getStabiliserHeightTrailing(), modelData.getStabiliserChord());
        verticalPolyline.addPoint(0, modelData.getStabiliserChord());
        verticalPolyline.addPoint(0, 0);
        returnList.add(verticalPolyline);
        double maxWidth = (modelData.getStabiliserSpanLeading() > modelData.getStabiliserSpanTrailing()) ? modelData.getStabiliserSpanLeading() : modelData.getStabiliserSpanTrailing();

        final SvgPolyline horizontalPolyline = new SvgPolyline(modelData, x + horizontalStabiliserOffset, y, modelData.getVStabiliserColour());
        horizontalPolyline.addPoint(maxWidth / 2 - modelData.getStabiliserSpanTrailing() / 2, 0);
        horizontalPolyline.addPoint(maxWidth / 2 + modelData.getStabiliserSpanTrailing() / 2, 0);
        horizontalPolyline.addPoint(maxWidth / 2 + modelData.getStabiliserSpanLeading() / 2, modelData.getStabiliserChord());
        horizontalPolyline.addPoint(maxWidth / 2 - modelData.getStabiliserSpanLeading() / 2, modelData.getStabiliserChord());
        horizontalPolyline.addPoint(maxWidth / 2 - modelData.getStabiliserSpanTrailing() / 2, 0);
        returnList.add(horizontalPolyline);
        return returnList;
    }
}
