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
 * Created on : Jun 22, 2013, 5:21:26 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class Ac3dFile {

    final private ModelData modelData;

    public Ac3dFile(ModelData modelData) {
        this.modelData = modelData;
    }
// http://www.inivis.com/ac3d/man/ac3dfileformat.html

    private String getBoxFaces() {
        return "numsurf 6\n"
                // small end cap
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "0 0 0\n"
                + "1 1 0\n"
                + "2 1 1\n"
                + "3 0 1\n"
                // large end cap
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "7 0 0\n"
                + "6 1 0\n"
                + "5 1 1\n"
                + "4 0 1\n"
                // side 1
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "4 0 0\n"
                + "5 1 0\n"
                + "1 1 1\n"
                + "0 0 1\n"
                // side 2
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "5 0 0\n"
                + "6 1 0\n"
                + "2 1 1\n"
                + "1 0 1\n"
                // side 3
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "6 0 0\n"
                + "7 1 0\n"
                + "3 1 1\n"
                + "2 0 1\n"
                // side 4
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "7 0 0\n"
                + "4 1 0\n"
                + "0 1 1\n"
                + "3 0 1\n";
    }

    private String getFuselageSection(double x, double y, double z, double length, double startWidth, double endWidth) {
        return "OBJECT poly\n"
                + "name \"fuselage\"\n"
                + "loc " + x + " " + y + " " + z + "\n"
                + "numvert 8\n"
                + -startWidth / 2 + " " + startWidth / 2 + " 0\n"
                + startWidth / 2 + " " + startWidth / 2 + " 0\n"
                + startWidth / 2 + " " + -startWidth / 2 + " 0\n"
                + -startWidth / 2 + " " + -startWidth / 2 + " 0\n"
                + -endWidth / 2 + " " + endWidth / 2 + " " + length + "\n"
                + endWidth / 2 + " " + endWidth / 2 + " " + length + "\n"
                + endWidth / 2 + " " + -endWidth / 2 + " " + length + "\n"
                + -endWidth / 2 + " " + -endWidth / 2 + " " + length + "\n"
                + getBoxFaces()
                + "kids 0\n";

    }

    private String getRectangle(double x, double y, double width, double hight) {
        return "OBJECT poly\n"
                + "name \"rect\"\n"
                + "loc 1 " + hight + " 0\n"
                + "numvert 4\n"
                + x + " " + hight + " 0\n"
                + width + " " + hight + " 0\n"
                + width + " " + y + " 0\n"
                + x + " " + y + " 0\n"
                + "numsurf 1\n"
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "3 0 0\n"
                + "2 1 0\n"
                + "1 1 1\n"
                + "0 0 1\n"
                + "kids 0\n";
    }

    private String getRotationMatrix(double angle) {
        double radians = Math.toRadians(angle);
        return "rot 1 0 0, "
                + "0 " + Math.cos(radians) + " " + -Math.sin(radians) + ", "
                + "0 " + Math.sin(radians) + " " + Math.cos(radians) + "\n";
    }

    private String getWing(double x, double y, double z, double chord, double length, double thickness, double angle) {
        return "OBJECT poly\n"
                + "name \"wing\"\n"
                + "loc " + x + " " + y + " " + z + "\n"
                + getRotationMatrix(angle)
                + "numvert 8\n"
                + 0 + " " + thickness / 2 + " 0\n"
                + length + " " + thickness / 2 + " 0\n"
                + length + " " + -thickness / 2 + " 0\n"
                + 0 + " " + -thickness / 2 + " 0\n"
                + 0 + " " + thickness / 2 + " " + chord + "\n"
                + length + " " + thickness / 2 + " " + chord + "\n"
                + length + " " + -thickness / 2 + " " + chord + "\n"
                + 0 + " " + -thickness / 2 + " " + chord + "\n"
                + getBoxFaces()
                + "kids 0\n";
    }

    private String getMainWing(double x, double y, double z, double chord, double span, double dihedral, double thickness) {
//        double mmOfsetByDihedral
        return "OBJECT poly\n"
                + "name \"mainwing\"\n"
                + "kids 2\n"
                + getWing(x, y, z, chord, span / 2, thickness, dihedral)
                + getWing(-span / 2, y, z, chord, span / 2, thickness, dihedral);
    }

    private double scaleToM(double mm) {
        return mm / 1000;
    }

    public String getAc3dFile() {
        return "AC3Db\n"
                + "MATERIAL \"\" rgb 0 1 1  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "OBJECT world\n"
                + "kids 4\n"
                //                + getRectangle(-1, -0.5, 1, 0.5)
                //                + getRectangle(5, 5, 1, 1)
                + getFuselageSection(0, 0, 0, scaleToM(modelData.getFuselageSectionLengthA()), scaleToM(modelData.getFuselageRadius()), scaleToM(modelData.getFuselageRadius() * 2))
                + getFuselageSection(0, 0, scaleToM(modelData.getFuselageSectionLengthA()), scaleToM(modelData.getFuselageSectionLengthB()), scaleToM(modelData.getFuselageRadius() * 2), scaleToM(modelData.getFuselageRadius() * 2))
                + getFuselageSection(0, 0, scaleToM(modelData.getFuselageSectionLengthA() + modelData.getFuselageSectionLengthB()), scaleToM(modelData.getFuselageSectionLengthC()), scaleToM(modelData.getFuselageRadius() * 2), scaleToM(modelData.getFuselageRadius()))
                + getMainWing(0, scaleToM(modelData.getFuselageRadius()), scaleToM(modelData.getFuselageSectionLengthA()), scaleToM(modelData.getChordLength()), scaleToM(modelData.getWingSpan()), modelData.getDihedralAngle(), scaleToM(5));
    }
}
