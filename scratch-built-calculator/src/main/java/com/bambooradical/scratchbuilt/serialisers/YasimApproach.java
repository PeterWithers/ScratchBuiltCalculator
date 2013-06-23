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
import javax.xml.bind.annotation.XmlElement;

/**
 * Created on : Jun 23, 2013, 5:25:41 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class YasimApproach {

    @XmlAttribute(name = "speed")
    public double getSpeed() {
        return 15;
    }

    @XmlAttribute(name = "aoa")
    public double getApproachAngleOfAttack() {
        return 4;
    }

    @XmlElement(name = "control-setting")
    public YasimControlSetting getYasimControlSetting() {
        return new YasimControlSetting(null, "/controls/engines/engine[0]/throttle", 1.0);
    }
}
