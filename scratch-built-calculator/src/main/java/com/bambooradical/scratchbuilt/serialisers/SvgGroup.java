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
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created on : Jun 24, 2013, 6:51:29 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class SvgGroup {

    protected ModelData modelData;
    protected double x;
    protected double y;
    private String id;

    public SvgGroup() {
    }

    public SvgGroup(ModelData modelData, double x, double y, String id) {
        this.modelData = modelData;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    protected double getHeight() {
        return 0;
    }

//    @XmlAttribute(name = "x")
//    public double getX() {
//        return x;
//    }
//
//    @XmlAttribute(name = "y")
//    public double getY() {
//        return y;
//    }
//    @XmlAttribute(name = "transform")
//    public String getTransform() {
//        return "scale(" + .5 + ") translate(" + x + "," + y + ")";
//    }
    @XmlAttribute(name = "id")
    public String getIs() {
        return id;
    }

    @XmlElement(name = "polyline", namespace = "http://www.w3.org/2000/svg")
    public List<SvgPolyline> getPolylines() {
        return null;
    }
}
