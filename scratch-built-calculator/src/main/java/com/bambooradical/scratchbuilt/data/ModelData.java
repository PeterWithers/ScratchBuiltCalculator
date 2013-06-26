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
 * Created on : Jun 22, 2013, 12:13:21 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public interface ModelData {

    public double getStabiliserRatio();

    public int getStabiliserArea();

    public int getElevatorArea();

    public int getFinArea();

    public double getDihedralAngle();

    public double getAttackAngle();

    public int getFuselageLength();

    public int getFuselageWidth();

    public int getFuselageHeight();

    public int getFuselageEndsDiameter();

    public int getStabiliserSpan();

    public int getStabiliserHeight();

    public int getStabiliserChord();

    public int getElevatorChord();

    public int getAileronStart();

    public int getAileronEnd();

    public int getAileronLength();

    public int getAileronChord();

    public int getChordLength();

    public int getWingLength();

    public int getWingSpan();

    public double getWingArea();

    public int getWingHeight();

    public FuselageSection[] getFuselageSections();

    public double getWeightInGrams();

    public Colour getMainWingColour();

    public Colour getAileronColour();

    public Colour getHStabiliserColour();

    public Colour getVStabiliserColour();

    public Colour getElevatorColour();

    public Colour getRudderColour();
}
