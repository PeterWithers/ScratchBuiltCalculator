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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Scanner;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on : Oct 12, 2013, 6:30 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@XmlRootElement(name = "svg", namespace = "http://www.w3.org/2000/svg")
public class SvgGcodeViewer {

    private final int area = 110;
    private final double scale = 3;
    private final double offset = 60;
    private final SvgPolyline svgFront = new SvgPolyline(null, 10 * scale, 10 * scale, Colour.WHITE);
    public final SvgPolyline svgSide = new SvgPolyline(null, 130 * scale, 10 * scale, Colour.WHITE);
    public final SvgPolyline svgTop = new SvgPolyline(null, 260 * scale, 10 * scale, Colour.WHITE);
    private ModelData modelData;

    public SvgGcodeViewer() {
    }

    public SvgGcodeViewer(ModelData modelData) {
        this.modelData = modelData;
    }

    private void addBoundingBox(SvgPolyline svgPolyline) {
        svgPolyline.addPoint(0, 0);
        svgPolyline.addPoint(0, area * scale);
        svgPolyline.addPoint(area * scale, area * scale);
        svgPolyline.addPoint(area * scale, 0);
        svgPolyline.addPoint(0, 0);
    }

    public void calculateDiagram() throws IOException {
        addBoundingBox(svgTop);
        addBoundingBox(svgFront);
        addBoundingBox(svgSide);
        final StringWriter stringWriter = new StringWriter();
        final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        new GcodeWing(modelData).getGcode(bufferedWriter);
        final Scanner scanner = new Scanner(stringWriter.getBuffer().toString());
        while (scanner.hasNext()) {
            final String nextLine = scanner.nextLine();
            if (nextLine.startsWith("G1") && nextLine.contains(" A")) {
                final String[] split = nextLine.split("[XYZ\\.]");
//                System.out.println("scanner.next():" + nextLine);
                final Integer x = Integer.decode(split[1]);
//                System.out.println("x:" + x);
                final Integer y = Integer.decode(split[3]);
//                System.out.println("y:" + y);
                final Integer z = Integer.decode(split[5]);
//                System.out.println("z:" + z);
                svgTop.addPoint(x * scale + offset * scale, y * scale + offset * scale);
                svgFront.addPoint(x * scale + offset * scale, area * scale - z * scale);
                svgSide.addPoint(y * scale + offset * scale, area * scale - z * scale);
            }
        }
    }

    @XmlAttribute(name = "version")
    protected double getVersion() {
        return 1.1;
    }

//    @XmlElement(name = "g", namespace = "http://www.w3.org/2000/svg")
//    public SvgGroup[] getGroups() {
//        final SvgWing svgWing = new SvgWing(modelData, 0, 0, "mainwing");
//        double fuselageOffsetY = svgWing.getHeight();
//        final FuselageSection[] fuselageSections = modelData.getFuselageSections();
//        final SvgStabilisers svgStabilisers = new SvgStabilisers(modelData, 0, fuselageOffsetY, "stabilisers");
//
//        final SvgElevator svgElevator = new SvgElevator(modelData, svgStabilisers.getWidth(), fuselageOffsetY, "elevator");
//        fuselageOffsetY += svgStabilisers.getHeight();
////       fuselageOffsetY += svgElevator.getHeight();
//        return new SvgGroup[]{svgWing, svgStabilisers, svgElevator,
//            new SvgFuselage(modelData, 0, fuselageOffsetY, fuselageSections[0]),
//            new SvgFuselage(modelData, fuselageSections[0].getLength(), fuselageOffsetY, fuselageSections[1]),
//            new SvgFuselage(modelData, fuselageSections[0].getLength() + fuselageSections[1].getLength(), fuselageOffsetY, fuselageSections[2])};
//    }
    @XmlElement(name = "polyline", namespace = "http://www.w3.org/2000/svg")
    public SvgPolyline[] getPolylines() throws IOException {
        calculateDiagram();
        return new SvgPolyline[]{svgFront, svgSide, svgTop};
    }
}
