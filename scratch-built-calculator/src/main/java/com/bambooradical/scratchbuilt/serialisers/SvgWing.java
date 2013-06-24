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
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgWing extends SvgPolyline {

    public SvgWing(ModelData modelData, double x, double y) {
        super(modelData, x, y);
    }

    @XmlAttribute(name = "points")
    public String getPoints() {
        return getScaledPoints(0, 0)
                + getScaledPoints(0, modelData.getWingSpan())
                + getScaledPoints(modelData.getChordLength(), modelData.getWingSpan())
                + getScaledPoints(modelData.getChordLength(), 0)
                + getScaledPoints(0, 0);
    }
}
