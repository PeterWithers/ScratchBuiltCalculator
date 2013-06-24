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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created on : Jun 23, 2013, 5:31:44 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class YasimWing {

    private ModelData modelData;
    private String comment = null;

    public YasimWing() {
    }
    // This defines the main wing of the aircraft. You can have only one (but see below about using vstab objects for extra lifting surfaces). The wing should have a <stall> subelement to indicate stall behavior, control surface subelements (flap0, flap1, spoiler, slat) to indicate what and where the control surfaces are, and <control> subelements to map user input properties to the control surfaces.

    public YasimWing(String comment, ModelData modelData) {
        this.modelData = modelData;
        this.comment = comment;
    }
    // x,y,z: The "base" of the wing, specified as the location of the mid-chord (not leading edge, trailing edge, or aerodynamic center) point at the root of the LEFT (!) wing.

    @XmlAttribute(name = "x")
    public double getX() {
        return modelData.getChordLength() / 2;
    }

    @XmlAttribute(name = "y")
    public double getY() {
        return -modelData.getFuselageRadius();
    }

    @XmlAttribute(name = "z")
    public double getZ() {
        return -modelData.getFuselageRadius();
    }
    // length: The length from the base of the wing to the midchord point at the tip. Note that this is not the same thing as span.

    @XmlAttribute(name = "length")
    public double getLength() {
        return modelData.getWingSpan() / 2;
    }
    // chord: The chord of the wing at its base, along the X axis (not normal to the leading edge, as it is sometimes defined).

    @XmlAttribute(name = "chord")
    public double getChord() {
        return modelData.getChordLength();
    }
    // incidence: The incidence angle at the wing root, in degrees. Zero is level with the fuselage (as in an aerobatic plane), positive means that the leading edge is higher than the trailing edge (as in a trainer).

    @XmlAttribute(name = "incidence")
    public double getIncidence() {
        return modelData.getAttackAngle();
    }
    // twist: The difference between the incidence angle at the wing root and the incidence angle at the wing tip. Typically, this is a negative number so that the wing tips have a lower angle of attack and stall after the wing root (washout).

    @XmlAttribute(name = "twist")
    public double getTwist() {
        return 0;
    }
    // taper: The taper fraction, expressed as the tip chord divided by the root chord. A taper of one is a hershey bar wing, and zero would be a wing ending at a point. Defaults to one.

    @XmlAttribute(name = "taper")
    public double getTaper() {
        return 0;
    }
    // sweep: The sweep angle of the wing, in degrees. Zero is no sweep, positive angles are swept back. Defaults to zero.

    @XmlAttribute(name = "sweep")
    public double getSweep() {
        return 0;
    }
    // dihedral: The dihedral angle of the wing. Positive angles are upward dihedral. Defaults to zero.

    @XmlAttribute(name = "dihedral")
    public double getDihedral() {
        return modelData.getDihedralAngle();
    }
    // idrag: Multiplier for the "induced drag" generated by this surface. In general, low aspect wings will generate less induced drag per-AoA than high aspect (glider) wings. This value isn't constrained well by the solution process, and may require tuning to get throttle settings correct in high AoA (approach) situations.

    @XmlAttribute(name = "idrag")
    public double getIdrag() {
        return 0;
    }
    // effectiveness: Multiplier for the "normal" drag generated by the wing. Defaults to 1. Arbitrary, dimensionless factor.

    @XmlAttribute(name = "effectiveness")
    public double getEffectiveness() {
        return 1;
    }
    // camber: The lift produced by the wing at zero angle of attack, expressed as a fraction of the maximum lift produced at the stall AoA.

    @XmlAttribute(name = "camber")
    public double getCamber() {
        return 0;
    }

    @XmlValue
    public String getComment() {
        if (comment == null) {
            return null;
        } else {
            return "<!-- " + comment + " -->";
        }
    }
}