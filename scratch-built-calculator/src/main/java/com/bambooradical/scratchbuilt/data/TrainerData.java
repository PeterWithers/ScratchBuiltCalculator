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

    private int chordRibLength = 120;
    private int aileronChord = chordRibLength / 3;
    private int chordLength = chordRibLength + aileronChord;
    private int strutSpacing = 50;
    private int strutsPerWing = 8;
    private int wingSpan = strutSpacing * strutsPerWing * 2;
    private double wingArea = ((wingSpan / 100.0) * ((chordLength) / 100.0));
    private int aileronLength = wingSpan / 4;
    private int dihedralAngle = 3;
    private int stabiliserChord = 80;
    private int stabiliserSpan = (int) (stabiliserChord * 3.0 / 2.0);
    private int elevatorChord = 20;
    private int stabiliserHeight = stabiliserChord;
    private int stabiliserArea = stabiliserSpan * 2 * stabiliserChord; // stabiliser area should be 15-20% of the wing area
    private int finArea = stabiliserHeight * stabiliserChord; //fin area should be 33% of the stabaliser
    private int elevatorArea = stabiliserSpan * 2 * elevatorChord; // elevator area should be 20-30% of the stabaliser area
    private int fuselageLength = (int) (wingSpan * 0.75);
//fuselageLength = chordLength*4.5;
    private int fuselageRadius = (int) (fuselageLength * 0.1 / 2);
    private double stabiliserRatio = (stabiliserSpan * 2.0) / stabiliserChord; // stabiliser ratio should be about 3:1
//ECHO: "stabiliserArea: ", 19200
//ECHO: "finArea: ", 6400
//ECHO: "elevatorArea: ", 4800
//ECHO: "noseCone(10,15,1.6,9.5,1,2);"
//ECHO: "fuselageRib(15,10,1,2,false)"
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

    @Override
    public double getStabiliserRatio() {
        return stabiliserRatio;
    }

    @Override
    public int getStabiliserArea() {
        return stabiliserArea;
    }

    @Override
    public int getElevatorArea() {
        return elevatorArea;
    }

    @Override
    public int getFinArea() {
        return finArea;
    }

    @Override
    public int getDihedralAngle() {
        return dihedralAngle;
    }

    @Override
    public int getFuselageLength() {
        return fuselageLength;
    }

    @Override
    public int getFuselageRadius() {
        return fuselageRadius;
    }

    @Override
    public int getStabiliserSpan() {
        return stabiliserSpan;
    }

    @Override
    public int getStabiliserHeight() {
        return stabiliserHeight;
    }

    @Override
    public int getStabiliserChord() {
        return stabiliserChord;
    }

    @Override
    public int getElevatorChord() {
        return elevatorChord;
    }

    @Override
    public int getAileronLength() {
        return aileronLength;
    }

    @Override
    public int getAileronChord() {
        return aileronChord;
    }

    @Override
    public int getChordLength() {
        return chordLength;
    }

    @Override
    public int getWingSpan() {
        return wingSpan;
    }

    @Override
    public double getWingArea() {
        return wingArea;
    }

    @Override
    public int getFuselageSectionLengthA() {
        return fuselageSectionLengthA;
    }

    @Override
    public int getFuselageSectionLengthB() {
        return fuselageSectionLengthB;
    }

    @Override
    public int getFuselageSectionLengthC() {
        return fuselageSectionLengthC;
    }
}
