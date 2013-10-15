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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
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

    private Gcode gcode;
    private final int area = 110;
    private final double scale = 3;
    private final double offset = 60;
    private SvgPolyline svgFront;
    private SvgPolyline svgSide;
    private SvgPolyline svgTop;
    private final ArrayList<SvgPolyline> polyLines = new ArrayList<SvgPolyline>();

    public SvgGcodeViewer() {
    }

    public SvgGcodeViewer(Gcode gcode) {
        this.gcode = gcode;
    }

    private void addBoundingBox(SvgPolyline svgPolyline) {
        svgPolyline.addPoint(0, 0);
        svgPolyline.addPoint(0, area * scale);
        svgPolyline.addPoint(area * scale, area * scale);
        svgPolyline.addPoint(area * scale, 0);
        svgPolyline.addPoint(0, 0);
    }

    private void renewPolyLines(Colour colourFill, Colour colourLine) {
        svgFront = new SvgPolyline(null, 10 * scale, 10 * scale, colourFill, colourLine);
        svgSide = new SvgPolyline(null, 130 * scale, 10 * scale, colourFill, colourLine);
        svgTop = new SvgPolyline(null, 260 * scale, 10 * scale, colourFill, colourLine);
        polyLines.add(svgFront);
        polyLines.add(svgSide);
        polyLines.add(svgTop);
    }

    public void calculateDiagram() throws IOException {
        renewPolyLines(Colour.WHITE, Colour.LIGHT_RED);
        addBoundingBox(svgTop);
        addBoundingBox(svgFront);
        addBoundingBox(svgSide);
        final StringWriter stringWriter = new StringWriter();
        final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        gcode.getGcode(bufferedWriter);
        final Scanner scanner = new Scanner(stringWriter.getBuffer().toString());
        double lastA = 0;
        boolean extruding = true;
        renewPolyLines(null, Colour.BLACK);
        while (scanner.hasNext()) {
            final String nextLine = scanner.nextLine();
            if (nextLine.startsWith("G1 ") && nextLine.contains(" X") && nextLine.contains(" Y") && nextLine.contains(" Z")) {
                final String[] split = nextLine.split("[XYZFA;]");
//                if (split.length > 5) {
//                    System.out.println("scanner.next():" + nextLine);
                final double x = Double.valueOf(split[1]);
//                System.out.println("x:" + x);
                final double y = Double.valueOf(split[2]);
//                System.out.println("y:" + y);
                final double z = Double.valueOf(split[3]).intValue();
//                System.out.println("z:" + z);
                final double a;
                if (nextLine.contains(" A")) {
                    a = Double.valueOf(split[5]);
//                    if (a > lastA) {
                    if (!extruding) {
                        renewPolyLines(null, Colour.BLACK);
                        extruding = true;
                    }
//                    }
//                System.out.println("a:" + a);
                } else {
                    if (extruding) {
                        renewPolyLines(null, Colour.LIGHT_GREEN);
                        extruding = false;
                    }
                    a = lastA;
                }
                if (z == 0) {
                    svgTop.addPoint(x * scale + offset * scale, y * scale + offset * scale);
                }
                final double yDepthHint = y * scale / 200;
                svgFront.addPoint(x * scale + offset * scale, area * scale - z * scale + yDepthHint);
                final double xDepthHint = x * scale / 200;
                svgSide.addPoint(y * scale + offset * scale, area * scale - z * scale + xDepthHint);
                lastA = a;
//                }
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
        return polyLines.toArray(new SvgPolyline[0]);
    }
}
