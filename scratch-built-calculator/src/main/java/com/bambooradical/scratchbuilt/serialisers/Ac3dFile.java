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
import com.bambooradical.scratchbuilt.data.FuselageSection;
import com.bambooradical.scratchbuilt.data.ModelData;
import java.text.DecimalFormat;

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

    // AC3D file format information can be found here: http://www.inivis.com/ac3d/man/ac3dfileformat.html
    private String getBoxFaces(int materialIndex) {
        return "numsurf 6\n"
                // small end cap
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "0 0 0\n"
                + "1 1 0\n"
                + "2 1 1\n"
                + "3 0 1\n"
                // large end cap
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "7 0 0\n"
                + "6 1 0\n"
                + "5 1 1\n"
                + "4 0 1\n"
                // side 1
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "4 0 0\n"
                + "5 1 0\n"
                + "1 1 1\n"
                + "0 0 1\n"
                // side 2
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "5 0 0\n"
                + "6 1 0\n"
                + "2 1 1\n"
                + "1 0 1\n"
                // side 3
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "6 0 0\n"
                + "7 1 0\n"
                + "3 1 1\n"
                + "2 0 1\n"
                // side 4
                + "SURF 0x20\n"
                + "mat " + materialIndex + "\n"
                + "refs 4\n"
                + "7 0 0\n"
                + "4 1 0\n"
                + "0 1 1\n"
                + "3 0 1\n";
    }

    private String getFuselageSection(double x, double y, double z, double length, double startWidth, double startHeight, double endWidth, double endHeight, int materialIndex) {
        return "OBJECT poly\n"
                + "name \"fuselage\"\n"
                + "loc " + x + " " + y + " " + z + "\n"
                + "numvert 8\n"
                + -startWidth / 2 + " " + startHeight / 2 + " 0\n"
                + startWidth / 2 + " " + startHeight / 2 + " 0\n"
                + startWidth / 2 + " " + -startHeight / 2 + " 0\n"
                + -startWidth / 2 + " " + -startHeight / 2 + " 0\n"
                + -endWidth / 2 + " " + endHeight / 2 + " " + length + "\n"
                + endWidth / 2 + " " + endHeight / 2 + " " + length + "\n"
                + endWidth / 2 + " " + -endHeight / 2 + " " + length + "\n"
                + -endWidth / 2 + " " + -endHeight / 2 + " " + length + "\n"
                + getBoxFaces(materialIndex)
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

    private String getRotationMatrix(double angleX, double angleY, double angleZ) {
        double radiansX = Math.toRadians(angleX);
        double radiansY = Math.toRadians(angleY);
        double radiansZ = Math.toRadians(angleZ);
        return "rot " + Math.cos(radiansY) * Math.cos(radiansZ) + " " + -Math.sin(radiansZ) + " " + Math.sin(radiansY) + ", "
                + Math.sin(radiansZ) + " " + Math.cos(radiansX) * Math.cos(radiansZ) + " " + -Math.sin(radiansX) + ", "
                + -Math.sin(radiansY) + " " + Math.sin(radiansX) + " " + Math.cos(radiansX) * Math.cos(radiansY) + "\n";
    }

    private String getWing(double chordStart, double chordEnd, double start, double end, double thickness, double angle, double attackAngle, int materialIndex) {
        return getTaperedWing(chordStart, chordEnd, chordStart, chordEnd, start, end, thickness, angle, attackAngle, materialIndex);
    }

    private String getTaperedWing(double chordStart, double chordEnd, double chordStartOuter, double chordEndOuter, double start, double end, double thickness, double angle, double attackAngle, int materialIndex) {
        return "OBJECT poly\n"
                + "name \"wing\"\n"
                //                + "loc " + x + " " + y + " " + 0 + "\n"
                + getRotationMatrix(-attackAngle, 0, angle)
                + "numvert 8\n"
                + start + " " + thickness / 2 + " " + chordStart + "\n"
                + end + " " + thickness / 2 + " " + chordStartOuter + "\n"
                + end + " " + -thickness / 2 + " " + chordStartOuter + "\n"
                + start + " " + -thickness / 2 + " " + chordStart + "\n"
                + start + " " + thickness / 2 + " " + chordEnd + "\n"
                + end + " " + thickness / 2 + " " + chordEndOuter + "\n"
                + end + " " + -thickness / 2 + " " + chordEndOuter + "\n"
                + start + " " + -thickness / 2 + " " + chordEnd + "\n"
                + getBoxFaces(materialIndex)
                + "kids 0\n";
    }

    private String getWingWithAileron(double chord, double length, double aileronStart, double aileronEnd, double aileronChord, double thickness) {
        String preAileronSection = getWing(0, chord, 0, aileronStart, thickness, 0, 0, 2);;
        String aileronSection = getWing(0, chord - aileronChord, aileronStart, aileronEnd, thickness, 0, 0, 2);;
        String aileron = getWing(chord - aileronChord, chord, aileronStart, aileronEnd, thickness, 0, 0, 1);
        String postAileronSection = getWing(0, chord, aileronEnd, length, thickness, 0, 0, 2);;
        return "OBJECT poly\n"
                + "name \"wingwithaileron\"\n"
                + "kids 4\n"
                + preAileronSection
                + aileronSection
                + aileron
                + postAileronSection;
    }

    private String getMainWing(double x, double y, double z, double chord, double length, double dihedral, double attackAngle, double aileronStart, double aileronEnd, double aileronChord, double thickness) {

        return "OBJECT poly\n"
                + "name \"mainwing\"\n"
                + "kids 2\n"
                + "OBJECT poly\n"
                + "name \"wingA\"\n"
                + "loc " + x + " " + y + " " + z + "\n"
                + getRotationMatrix(-attackAngle, 0, -dihedral)
                + getWingWithAileron(chord, length, aileronStart, aileronEnd, aileronChord, thickness)
                + "kids 0\n"
                + "OBJECT poly\n"
                + "name \"wingB\"\n"
                + "loc " + x + " " + y + " " + z + "\n"
                + getRotationMatrix(-attackAngle, 0, 180 + dihedral)
                //                + "loc " + 0 + " " + 0 + " "  + "\n"
                + getWingWithAileron(chord, length, aileronStart, aileronEnd, aileronChord, thickness)
                + "kids 0\n";
    }

    private String getTailWing(double x, double y, double z, double chordHorizontal, double chordVertical, double spanHorizontal, double spanVertical, double thickness) {
        return "OBJECT poly\n"
                + "name \"tailwing\"\n"
                + "kids 3\n"
                + getTaperedWing(z, chordHorizontal + z, chordHorizontal / 2 + z, chordHorizontal + z, 0, spanHorizontal / 2, thickness, 0, 0, 3)
                + getTaperedWing(z, chordVertical + z, chordHorizontal / 2 + z, chordVertical + z, 0, spanVertical, thickness, -90, 0, 4)
                + getTaperedWing(z, chordHorizontal + z, chordHorizontal / 2 + z, chordHorizontal + z, 0, spanHorizontal / 2, thickness, 180, 0, 3);
    }

    private double scaleToM(double mm) {
        return mm / 1000;
    }

    protected String getFormattedColour(Colour colour) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(colour.getRed() / 255.0) + " " + decimalFormat.format(colour.getGreen() / 255.0) + " " + decimalFormat.format(colour.getBlue() / 255.0);
    }

    public String getAc3dFile() {
        FuselageSection[] fuselageSections = modelData.getFuselageSections();
        return "AC3Db\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(modelData.getAileronColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(modelData.getElevatorColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(modelData.getMainWingColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(modelData.getHStabiliserColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(modelData.getVStabiliserColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(fuselageSections[0].getColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(fuselageSections[1].getColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "MATERIAL \"\" rgb " + getFormattedColour(fuselageSections[2].getColour()) + "  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n" + "OBJECT world\n"
                + "kids 5\n"
                + getFuselageSection(0, 0, scaleToM(fuselageSections[0].getStart()), scaleToM(fuselageSections[0].getLength()), scaleToM(fuselageSections[0].getStartWidth()), scaleToM(fuselageSections[0].getStartHeight()), scaleToM(fuselageSections[0].getEndWidth()), scaleToM(fuselageSections[0].getEndHeight()), 5)
                + getFuselageSection(0, 0, scaleToM(fuselageSections[1].getStart()), scaleToM(fuselageSections[1].getLength()), scaleToM(fuselageSections[1].getStartWidth()), scaleToM(fuselageSections[1].getStartHeight()), scaleToM(fuselageSections[1].getEndWidth()), scaleToM(fuselageSections[1].getEndHeight()), 6)
                + getFuselageSection(0, 0, scaleToM(fuselageSections[2].getStart()), scaleToM(fuselageSections[2].getLength()), scaleToM(fuselageSections[2].getStartWidth()), scaleToM(fuselageSections[2].getStartHeight()), scaleToM(fuselageSections[2].getEndWidth()), scaleToM(fuselageSections[2].getEndHeight()), 7)
                + getMainWing(0, scaleToM(modelData.getWingHeight()), scaleToM(fuselageSections[1].getStart()), scaleToM(modelData.getChordLength()), scaleToM(modelData.getWingLength()), modelData.getDihedralAngle(), modelData.getAttackAngle(), scaleToM(modelData.getAileronStart()), scaleToM(modelData.getAileronEnd()), scaleToM(modelData.getAileronChord()), scaleToM(5))
                + getTailWing(0, 0, scaleToM(fuselageSections[2].getEnd() - modelData.getStabiliserChord() / 2), scaleToM(modelData.getStabiliserChord()), scaleToM(modelData.getStabiliserChord()), scaleToM(modelData.getStabiliserSpan()), scaleToM(modelData.getStabiliserHeight()), scaleToM(5));
    }
}
