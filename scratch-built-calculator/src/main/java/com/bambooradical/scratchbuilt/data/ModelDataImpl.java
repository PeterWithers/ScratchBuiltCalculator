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
    private final int wingHeight;
    private final int aileronStart;
    private final int aileronEnd;
    private final double dihedralAngle;
    private final double attackAngle;
    private final int stabiliserChord = 80;
    private final int stabiliserSpan = (int) (stabiliserChord * 3.0 / 2.0);
    private final int elevatorChord = 20;
    private final int stabiliserHeight;
    private int stabiliserArea = stabiliserSpan * 2 * stabiliserChord; // stabiliser area should be 15-20% of the wing area
    private int finArea;
    private final int fuselageLength;
//fuselageLength = chordLength*4.5;
//    private final int fuselageRadius;
    private final int fuselageWidth;
    private final int fuselageHeight;
    private final int fuselageEndsDiameter;
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
    int fuselageSectionLength = 160 + 160 + 280;
    int weightInGrams = 500;

    public ModelDataImpl(int chordLength, int wingSpan, double dihedralAngle, double attackAngle, int aileronEnd, int aileronStart, int aileronChord, int wingHeight, int fuselageHeight, int fuselageWidth, int fuselageEndsDiameter) {
        this.chordLength = chordLength;
        this.wingSpan = wingSpan;
        wingSpan = strutSpacing * strutsPerWing * 2;
//        aileronLength = wingSpan / 4 - 5;
//        chordLength = chordRibLength + aileronChord;
        wingArea = ((wingSpan / 100.0) * ((chordLength) / 100.0));
        chordRibLength = 120;
        this.aileronChord = aileronChord;
        fuselageLength = (int) (wingSpan * 0.75);
        this.fuselageHeight = fuselageHeight;
        this.fuselageWidth = fuselageWidth;
        this.fuselageEndsDiameter = fuselageEndsDiameter;
        stabiliserHeight = stabiliserChord;
        finArea = stabiliserHeight * stabiliserChord; //fin area should be 33% of the stabaliser
        this.dihedralAngle = dihedralAngle;
        this.attackAngle = attackAngle;
        this.aileronStart = aileronStart;
        this.aileronEnd = aileronEnd;
        this.wingHeight = wingHeight;

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
        return stabiliserSpan * 2 * elevatorChord; // elevator area should be 20-30% of the stabaliser area;
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
    public int getFuselageHeight() {
        return fuselageHeight;
    }

    @Override
    public int getFuselageEndsDiameter() {
        return fuselageEndsDiameter;
    }

    @Override
    public int getFuselageWidth() {
        return fuselageWidth;
    }

    @Override
    public int getStabiliserChord() {
        return stabiliserChord;
    }

    @Override
    public int getStabiliserSpanLeading() {
        return stabiliserSpan;
    }

    @Override
    public int getStabiliserSpanTrailing() {
        return stabiliserSpan;
    }

    @Override
    public int getStabiliserHeightLeading() {
        return stabiliserSpan;
    }

    @Override
    public int getStabiliserHeightTrailing() {
        return stabiliserHeight;
    }

    @Override
    public int getElevatorChord() {
        return elevatorChord;
    }

    @Override
    public int getElevatorSpan() {
        return chordLength;
    }

    @Override
    public int getAileronStart() {
        return aileronStart;
    }

    @Override
    public int getAileronEnd() {
        return aileronEnd;
    }

    @Override
    public int getAileronLength() {
        return aileronEnd - aileronStart;
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
    public int getWingHeight() {
        return wingHeight;
    }

    @Override
    public int getWingLength() {
        return wingSpan / 2;
    }

    @Override
    public double getWingArea() {
        return wingArea;
    }

    @Override
    public FuselageSection[] getFuselageSections() {
        int firstSectionLength = getChordLength();
        int secondSectionLength = (int) Math.abs(getChordLength() * Math.cos(Math.toRadians(getAttackAngle())));
        int thirdSectionLength = fuselageSectionLength - firstSectionLength - secondSectionLength;
        return new FuselageSection[]{
                    new FuselageSection(fuselageEndsDiameter, fuselageEndsDiameter, fuselageWidth, fuselageHeight, 0, firstSectionLength, getRudderColour(), true, "fuselagePartA"),
                    new FuselageSection(fuselageWidth, fuselageHeight, fuselageWidth, fuselageHeight, firstSectionLength, secondSectionLength, getMainWingColour(), false, "fuselagePartB"),
                    new FuselageSection(fuselageWidth, fuselageHeight, fuselageEndsDiameter, fuselageEndsDiameter, firstSectionLength + secondSectionLength, thirdSectionLength, getRudderColour(), true, "fuselagePartC")
                };
    }

    @Override
    public double getWeightInGrams() {
        return weightInGrams;
    }

    @Override
    public Colour getMainWingColour() {
        return new Colour(0xC20048);
    }

    @Override
    public Colour getAileronColour() {
        return new Colour(0xC20018);
    }

    @Override
    public Colour getHStabiliserColour() {
        return new Colour(0xC21800);
    }

    @Override
    public Colour getVStabiliserColour() {
        return new Colour(0xC24900);
    }

    @Override
    public Colour getElevatorColour() {
        return new Colour(0xC2AA00);
    }

    @Override
    public Colour getRudderColour() {
        return new Colour(0xC27900);
    }
}
