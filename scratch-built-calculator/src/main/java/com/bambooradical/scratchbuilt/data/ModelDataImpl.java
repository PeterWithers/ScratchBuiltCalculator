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
 * Created on : Jun 23, 2013, 1:45:54 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class ModelDataImpl implements ModelData {

    private int chordRibLength;
    private final int aileronChord;
    private final int chordLength;
    private int strutSpacing = 50;
    private int strutsPerWing = 8;
    private final int wingSpan;
    private double wingArea;
    private final int aileronLength;
    private final double dihedralAngle;
    private final double attackAngle;
    private final int stabiliserChord = 80;
    private final int stabiliserSpan = (int) (stabiliserChord * 3.0 / 2.0);
    private final int elevatorChord = 20;
    private final int stabiliserHeight;
    private int stabiliserArea = stabiliserSpan * 2 * stabiliserChord; // stabiliser area should be 15-20% of the wing area
    private int finArea;
    private int elevatorArea = stabiliserSpan * 2 * elevatorChord; // elevator area should be 20-30% of the stabaliser area
    private final int fuselageLength;
//fuselageLength = chordLength*4.5;
    private final int fuselageRadius;
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

    public ModelDataImpl(int aileronChord, int chordLength, int wingSpan, int aileronLength, double dihedralAngle, double attackAngle, int stabiliserHeight, int fuselageLength, int fuselageRadius) {
        this.aileronChord = aileronChord;
        this.chordLength = chordLength;
        this.wingSpan = wingSpan;
        this.aileronLength = aileronLength;
        this.dihedralAngle = dihedralAngle;
        this.attackAngle = attackAngle;
        this.stabiliserHeight = stabiliserHeight;
        this.fuselageLength = fuselageLength;
        this.fuselageRadius = fuselageRadius;
    }

    public ModelDataImpl(int chordLength, int wingSpan, double dihedralAngle, double attackAngle) {
        this.chordLength = chordLength;
        this.wingSpan = wingSpan;
        wingSpan = strutSpacing * strutsPerWing * 2;
        aileronLength = wingSpan / 4;
//        chordLength = chordRibLength + aileronChord;
        wingArea = ((wingSpan / 100.0) * ((chordLength) / 100.0));
        chordRibLength = 120;
        aileronChord = chordRibLength / 3;
        fuselageLength = (int) (wingSpan * 0.75);
        fuselageRadius = (int) (fuselageLength * 0.1 / 2);
        stabiliserHeight = stabiliserChord;
        finArea = stabiliserHeight * stabiliserChord; //fin area should be 33% of the stabaliser
        this.dihedralAngle = dihedralAngle;
        this.attackAngle = attackAngle;
    }

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
    public double getAttackAngle() {
        return attackAngle;
    }

    @Override
    public double getDihedralAngle() {
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
