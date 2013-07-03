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

/**
 * Created on : July 3, 2013, 19:32 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class AircraftSetSim {

    private ModelData modelData;

    public AircraftSetSim() {
    }
    // config file information can be found at: http://wiki.flightgear.org/Aircraft-set.xml

    public AircraftSetSim(ModelData modelData) {
        this.modelData = modelData;
    }

    @XmlElement(name = "description")
    public String getAircraftDiscription() {
        // The text that is to be shown in the aircraft selection dialog of FGRun and other GUIs.
        return "";
    }

    @XmlElement(name = "aircraft-version")
    public String getAircraftVersion() {
        // Commonly shows the latest update date.
        return "";
    }

    @XmlElement(name = "status")
    public String getAircraftStatus() {
        // The status of the development of your plane. This can for example be used in order to get a list of aircraft with a certain minimum development status by passing the --min-status= parameter to the fgfs executable.
        // See Formalizing Aircraft Status for guidelines.
        return "";
    }

    @XmlElement(name = "author")
    public String getAircraftAuthor() {
        // Place your name(s) here. If multiple people worked on the same aircraft, you can place the specific development area for each person between ( ).
        return "";
    }

    @XmlElement(name = "flight-model")
    public String getAircraftFDM() {
        // What FDM system is the aircraft using. Set to 'yasim' for YASim and to 'jsb' for JSBSim.
        return "";
    }

    @XmlElement(name = "aero")
    public String getAircraftAero() {
        // The filename of the FDM file, without .xml.
//For instance, if your FDM file is sopwithCamel1F1.xml, the aero section would look like:
//<aero>sopwithCamel1F1</aero>
//You have created this file earlier on at: #The .xml file (FDM)
        return "";
    }

    @XmlElement(name = "splash-texture")
    public String getAircraftSplashTexture() {
        // Place the location of an image you would like to show during loading of the aircraft. FlightGear will randomly pick one of the generic splashes in $FG ROOT/Textures/ when this line is not found in the -set.xml file.
        return "";
    }

    @XmlElement(name = "splash-title")
    public String getAircraftSplashTitle() {
        // Adds small text (above the splash texture) to the loading screen.
        return "";
    }

    @XmlElement(name = "model")
    public AircraftPath getAircraftModel() {
        // Where to find the model. Could be an .ac file (plain model format) or .xml if you want to have extras like animations etc.
        return new AircraftPath("");
    }

    @XmlElement(name = "splash-title")
    public AircraftPath getAircraftSound() {
        // The path to your aircraft's sound file, do not include if you don't have one. The pitch and volume of the sound is controlled by the xml file and throttle position. If for example you find a nice wav file of the jet engine you're using, you can fix it up with your favorite sound editor so it loops nicely and include that into your model (I've noticed a few models where it can get quite annoying when the loop length is so small you can really notice it) so make it smooth.
// For additional information, you'll want to check out $FG ROOT/Docs/README.xmlsound
        return new AircraftPath("");
    }

    @XmlElement(name = "panel")
    public AircraftPath getAutopilot() {
        // Where to find the (2D) panel. We prefer 3D cockpits, which are linked in the model .xml file.
        return new AircraftPath("");
    }
}
