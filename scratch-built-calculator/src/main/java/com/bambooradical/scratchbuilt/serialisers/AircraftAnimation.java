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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created on : July 3, 2013, 19:32 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@XmlRootElement(name = "PropertyList")
public class AircraftAnimation {

    private ModelData modelData;

    public AircraftAnimation() {
    }
    // config file information can be found at: http://wiki.flightgear.org/Howto:Animate_models

    public AircraftAnimation(ModelData modelData) {
        this.modelData = modelData;
    }

    @XmlElement(name = "path")
    public String getAcPath() {
        return "scratch-built.ac";
    }
}
