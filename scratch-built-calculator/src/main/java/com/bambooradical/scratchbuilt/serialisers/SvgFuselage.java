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

import com.bambooradical.scratchbuilt.data.FuselageSection;
import com.bambooradical.scratchbuilt.data.ModelData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgFuselage extends SvgGroup {

    private FuselageSection fuselageSection;

    public SvgFuselage() {
    }

    public SvgFuselage(ModelData modelData, double x, double y, FuselageSection fuselageSection) {
        super(modelData, x, y, fuselageSection.getLabel());
        this.fuselageSection = fuselageSection;
    }

    @Override
    protected double getHeight() {
        // todo: this is not really correct and will often over estimate the height
        return Math.max(fuselageSection.getEndHeight(), fuselageSection.getStartHeight())
                + Math.max(fuselageSection.getEndWidth(), fuselageSection.getStartWidth());
    }

    @Override
    protected double getWidth() {
        return fuselageSection.getLength();
    }

    private SvgPolyline getPolyline(double offsetX, double offsetY, double start, double end, double length) {
        final SvgPolyline svgPolyline = new SvgPolyline(modelData, x + offsetX, y + offsetY, fuselageSection.getColour());
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

    public double getOptimalOffset(double lastStart, double lastEnd, double nextStart, double nextEnd) {
        double largestLast = (lastStart > lastEnd) ? lastStart : lastEnd;
//        double smallestLast = (lastStart < lastEnd) ? lastStart : lastEnd;
        double largestNext = (nextStart > nextEnd) ? nextStart : nextEnd;
        double lastGap;
        double nextGap;
        if ((lastStart + nextStart) >= (lastEnd + nextEnd)) {
            // calculate the offset based on the start 
            lastGap = (largestLast - lastStart) / 2;
            nextGap = (largestNext - nextStart) / 2;
        } else {
            // calculate the offset based on the  end
            lastGap = (largestLast - lastEnd) / 2;
            nextGap = (largestNext - nextEnd) / 2;
        }
        return largestLast - lastGap - nextGap;
    }

    @Override
    public List<SvgPolyline> getPolylines() {
        final ArrayList<SvgPolyline> returnList = new ArrayList<SvgPolyline>();
        double startHeight = fuselageSection.getStartHeight();
        double endHeight = fuselageSection.getEndHeight();
        double startWidth = fuselageSection.getStartWidth();
        double endWidth = fuselageSection.getEndWidth();
        double length = fuselageSection.getLength();
        double currentOffset = 0;
        final SvgPolyline leftSide = getPolyline(0, currentOffset, startHeight, endHeight, length);
        currentOffset += getOptimalOffset(startHeight, endHeight, endHeight, startHeight);
        final SvgPolyline rightSide = getPolyline(0, currentOffset, endHeight, startHeight, length);
        currentOffset += getOptimalOffset(endHeight, startHeight, startWidth, endWidth);
        final SvgPolyline bottom = getPolyline(0, currentOffset, startWidth, endWidth, length);
        currentOffset += getOptimalOffset(startWidth, endWidth, endWidth, startWidth);
        final SvgPolyline top = getPolyline(0, currentOffset, endWidth, startWidth, length);
        if (fuselageSection.isRequiresTop()) {
            returnList.add(leftSide);
            returnList.add(rightSide);
            returnList.add(bottom);
            returnList.add(top);
        } else {
            returnList.add(leftSide);
            returnList.add(rightSide);
            returnList.add(bottom);
        }
        return returnList;
    }
}
