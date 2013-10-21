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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on : Oct 21, 2013, 21:42
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@XmlRootElement(name = "svg", namespace = "http://www.w3.org/2000/svg")
public class SvgReport {

    final private ModelData modelData;
    final private List<SvgText> svgTextList;
    final private double width;
    final private double height;

    public SvgReport() {
        modelData = null;
        svgTextList = null;
        width = 0;
        height = 0;
    }

    public SvgReport(ModelData modelData) {
        this.modelData = modelData;
        this.svgTextList = new ArrayList<SvgText>();
        svgTextList.add(new SvgText(10, 10, "wing area: " + modelData.getWingAreaDm2() + " dm2"));
        svgTextList.add(new SvgText(10, 20, "wing loading: " + modelData.getWingLoading()));
        svgTextList.add(new SvgText(10, 30, "cubic loading: " + modelData.getCubicWingLoading()));

        height = 40;
        width = 400;
    }

    @XmlAttribute(name = "viewBox")
    protected String getViewBox() {
        return "0 0 " + width + " " + height;
    }

    @XmlAttribute(name = "version")
    protected double getVersion() {
        return 1.1;
    }

//    @XmlElement(name = "g", namespace = "http://www.w3.org/2000/svg")
//    public SvgGroup[] getGroups() {
//        return svgGroups;
//    }
    @XmlElement(name = "text", namespace = "http://www.w3.org/2000/svg")
    public List<SvgText> getLabels() {
        return svgTextList;
    }

    @XmlElement(name = "polyline", namespace = "http://www.w3.org/2000/svg")
    public SvgPolyline[] getPolylines() {
        return new SvgPolyline[]{new SvgPolyline()};
    }
}
