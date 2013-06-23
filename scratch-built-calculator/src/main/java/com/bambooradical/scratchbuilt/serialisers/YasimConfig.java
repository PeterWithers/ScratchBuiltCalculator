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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on : Jun 23, 2013, 4:24:48 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@XmlRootElement(name = "airplane")// The top-level element for the file.
public class YasimConfig {

    private ModelData modelData;

    public YasimConfig() {
    }
    // confi file information can be found at: http://wiki.flightgear.org/YASim

    public YasimConfig(ModelData modelData) {
        this.modelData = modelData;
    }

    @XmlAttribute(name = "mass")
    public double getMass() {
        // The empty (no fuel) weight, in pounds. It does include the weight of the engine(s), so when you add the engine weight in its tag, it acts just like a ballast.
        return 1;
    }

    @XmlElement(name = "approach")
    public YasimApproach getYasimApproach() {
        // The approach parameters for the aircraft. The solver will generate an aircraft that matches these settings. The element can (and should) contain <control> elements indicating pilot input settings, such as flaps and throttle, for the approach.
        return new YasimApproach();
    }

    @XmlElement(name = "cruise")
    public YasimCruise getYasimCruse() {
        // The cruise speed and altitude for the solver to match. As above, this should contain <control> elements indicating aircraft configuration. Especially, make sure the engines are generating enough thrust at cruise!
        return new YasimCruise();
    }

    @XmlElement(name = "cockpit")
    public YasimCockpit getYasimCockpit() {
        // The location of the cockpit (pilot eyepoint).
        return new YasimCockpit();
    }
}
