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
package com.bambooradical.scratchbuilt.data;

/**
 * Created on : Jun 22, 2013, 11:58:04 AM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class TrainerData implements ModelData {

    int stabiliserRatio; // stabiliser ratio should be about 3:1
    int stabiliserArea; //stabiliser area should be 15-20% of the wing area
    int elevatorArea; // elevator area should be 20-30% of the stabaliser area
    int finArea; //fin area should be 33% of the stabaliser
    int dihedralAngle = 3; // dihedral angle: 3
    int fuselageLength = 600; // fuselageLength: 600
    int fuselageRadius = 30; // fuselageRadius: 30
    int stabiliserSpan = 120; // stabiliserSpan 120
    int stabiliserHeight = 80; // stabiliserHeight: 80
    int stabiliserChord = 80; // stabiliserChord: 80
//ECHO: "stabiliserArea: ", 19200
//ECHO: "finArea: ", 6400
//ECHO: "elevatorArea: ", 4800
    int elevatorChord = 20;
    int aileronLength = 200;
    int aileronChord = 40;
//ECHO: "noseCone(10,15,1.6,9.5,1,2);"
//ECHO: "fuselageRib(15,10,1,2,false)"
    int chordLength = 160;
    int wingSpan = 800;
//ECHO: "wingspan should be 5-6 times the chord: ", 5
//ECHO: "wing area: ", 12.8, "dm2"
//ECHO: "empennageStruts(80,120,80,20);"
//ECHO: "horizontalStabiliser(112.8,169.2,112.8);"
//ECHO: "verticalStabiliser(112.8,169.2,112.8);"
//ECHO: "horizontalStabiliser(112.8,169.2,112.8);"
//ECHO: "elevator(80,120,112.8,20);"
    int fuselageSectionLengthA = 160;
    int fuselageSectionLengthB = 160;
    int fuselageSectionLengthC = 280;

    public int getStabiliserRatio() {
        return stabiliserRatio;
    }

    public int getStabiliserArea() {
        return stabiliserArea;
    }

    public int getElevatorArea() {
        return elevatorArea;
    }

    public int getFinArea() {
        return finArea;
    }

    public int getDihedralAngle() {
        return dihedralAngle;
    }

    public int getFuselageLength() {
        return fuselageLength;
    }

    public int getFuselageRadius() {
        return fuselageRadius;
    }

    public int getStabiliserSpan() {
        return stabiliserSpan;
    }

    public int getStabiliserHeight() {
        return stabiliserHeight;
    }

    public int getStabiliserChord() {
        return stabiliserChord;
    }

    public int getElevatorChord() {
        return elevatorChord;
    }

    public int getAileronLength() {
        return aileronLength;
    }

    public int getAileronChord() {
        return aileronChord;
    }

    public int getChordLength() {
        return chordLength;
    }

    public int getWingSpan() {
        return wingSpan;
    }

    public int getFuselageSectionLengthA() {
        return fuselageSectionLengthA;
    }

    public int getFuselageSectionLengthB() {
        return fuselageSectionLengthB;
    }

    public int getFuselageSectionLengthC() {
        return fuselageSectionLengthC;
    }
}
