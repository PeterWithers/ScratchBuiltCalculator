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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class TrainerDataTest {

    /**
     * Test of getStabiliserRatio method, of class TrainerData.
     */
    @Test
    public void testGetStabiliserRatio() {
        System.out.println("getStabiliserRatio");
        TrainerData instance = new TrainerData();
        double expResult = 3;
        double result = instance.getStabiliserRatio();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getStabiliserArea method, of class TrainerData.
     */
    @Test
    public void testGetStabiliserArea() {
        System.out.println("getStabiliserArea");
        TrainerData instance = new TrainerData();
        int expResult = 19200;
        int result = instance.getStabiliserArea();
        assertEquals(expResult, result);
    }

    /**
     * Test of getElevatorArea method, of class TrainerData.
     */
    @Test
    public void testGetElevatorArea() {
        System.out.println("getElevatorArea");
        TrainerData instance = new TrainerData();
        int expResult = 4800;
        int result = instance.getElevatorArea();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFinArea method, of class TrainerData.
     */
    @Test
    public void testGetFinArea() {
        System.out.println("getFinArea");
        TrainerData instance = new TrainerData();
        int expResult = 6400;
        int result = instance.getFinArea();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDihedralAngle method, of class TrainerData.
     */
    @Test
    public void testGetDihedralAngle() {
        System.out.println("getDihedralAngle");
        TrainerData instance = new TrainerData();
        double expResult = 3;
        double result = instance.getDihedralAngle();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of getAttackAngle method, of class TrainerData.
     */
    @Test
    public void testGetAttackAngle() {
        System.out.println("getAttackAngle");
        TrainerData instance = new TrainerData();
        double expResult = 3;
        double result = instance.getAttackAngle();
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of getFuselageLength method, of class TrainerData.
     */
    @Test
    public void testGetFuselageLength() {
        System.out.println("getFuselageLength");
        TrainerData instance = new TrainerData();
        int expResult = 600;
        int result = instance.getFuselageLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFuselageRadius method, of class TrainerData.
     */
    @Test
    public void testGetFuselageRadius() {
        System.out.println("getFuselageRadius");
        TrainerData instance = new TrainerData();
        int expResult = 60;
        int result = instance.getFuselageWidth();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStabiliserSpan method, of class TrainerData.
     */
    @Test
    public void testGetStabiliserSpan() {
        System.out.println("getStabiliserSpan");
        TrainerData instance = new TrainerData();
        assertEquals(160, instance.getStabiliserSpanLeading());
        assertEquals(320, instance.getStabiliserSpanTrailing());
    }

    /**
     * Test of getStabiliserHeight method, of class TrainerData.
     */
    @Test
    public void testGetStabiliserHeight() {
        System.out.println("getStabiliserHeight");
        TrainerData instance = new TrainerData();
        assertEquals(40, instance.getStabiliserHeightLeading());
        assertEquals(120, instance.getStabiliserHeightTrailing());
    }

    /**
     * Test of getStabiliserChord method, of class TrainerData.
     */
    @Test
    public void testGetStabiliserChord() {
        System.out.println("getStabiliserChord");
        TrainerData instance = new TrainerData();
        int expResult = 80;
        int result = instance.getStabiliserChord();
        assertEquals(expResult, result);
    }

    /**
     * Test of getElevatorChord method, of class TrainerData.
     */
    @Test
    public void testGetElevatorChord() {
        System.out.println("getElevatorChord");
        TrainerData instance = new TrainerData();
        int expResult = 20;
        int result = instance.getElevatorChord();
        assertEquals(expResult, result);
    }

    /**
     * Test of getElevatorSpan method, of class TrainerData.
     */
    @Test
    public void testGetElevatorSpan() {
        System.out.println("getElevatorSpan");
        TrainerData instance = new TrainerData();
        int expResult = 160;
        int result = instance.getElevatorSpan();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAileronLength method, of class TrainerData.
     */
    @Test
    public void testGetAileronLength() {
        System.out.println("getAileronLength");
        TrainerData instance = new TrainerData();
        int expResult = 200;
        int result = instance.getAileronLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAileronChord method, of class TrainerData.
     */
    @Test
    public void testGetAileronChord() {
        System.out.println("getAileronChord");
        TrainerData instance = new TrainerData();
        int expResult = 40;
        int result = instance.getAileronChord();
        assertEquals(expResult, result);
    }

    /**
     * Test of getChordLength method, of class TrainerData.
     */
    @Test
    public void testGetChordLength() {
        System.out.println("getChordLength");
        TrainerData instance = new TrainerData();
        int expResult = 160;
        int result = instance.getChordLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWingSpan method, of class TrainerData.
     */
    @Test
    public void testGetWingSpan() {
        System.out.println("getWingSpan");
        TrainerData instance = new TrainerData();
        int expResult = 800;
        int result = instance.getWingSpan();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFuselageSectionLengthA method, of class TrainerData.
     */
    @Test
    public void testGetFuselageSectionLengthA() {
        System.out.println("getFuselageSectionLengthA");
        TrainerData instance = new TrainerData();
        int expResult = 160;
        int result = instance.getFuselageSections()[0].getLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFuselageSectionLengthB method, of class TrainerData.
     */
    @Test
    public void testGetFuselageSectionLengthB() {
        System.out.println("getFuselageSectionLengthB");
        TrainerData instance = new TrainerData();
        int expResult = 159;
        int result = instance.getFuselageSections()[1].getLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFuselageSectionLengthC method, of class TrainerData.
     */
    @Test
    public void testGetFuselageSectionLengthC() {
        System.out.println("getFuselageSectionLengthC");
        TrainerData instance = new TrainerData();
        int expResult = 281;
        int result = instance.getFuselageSections()[2].getLength();
        assertEquals(expResult, result);
    }

    /**
     * Test of getWingArea method, of class TrainerData.
     */
    @Test
    public void testGetWingArea() {
        System.out.println("getWingArea");
        TrainerData instance = new TrainerData();
        double expResult = 12.8;
        double result = instance.getWingArea();
        assertEquals(expResult, result, 0);
    }
}