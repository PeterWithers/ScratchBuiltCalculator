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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * Created on : Jun 23, 2013, 5:31:44 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class YasimControlSetting {

    private String comment = null;
    private String axis;
    private double value;

    public YasimControlSetting() {
    }

    public YasimControlSetting(String comment, String axis, double value) {
        this.comment = comment;
        this.axis = axis;
        this.value = value;
    }

    @XmlAttribute(name = "speed")
    public String getAxis() {
        return axis;
    }

    @XmlAttribute(name = "value")
    public double getControlValue() {
        return value;
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
