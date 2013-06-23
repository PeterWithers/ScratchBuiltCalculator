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
package com.bambooradical.scratchbuilt.rest;

import com.bambooradical.scratchbuilt.data.ModelDataImpl;
import com.bambooradical.scratchbuilt.serialisers.Ac3dFile;
import com.bambooradical.scratchbuilt.serialisers.YasimConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created on : Jun 22, 2013, 5:06:33 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@Path("/calculator")
public class CalculatorResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("ac3d")
    public String getAc3dFile(@Context HttpServletRequest httpServletRequest,
            @DefaultValue("800") @QueryParam("wingSpan") int wingSpan,
            @DefaultValue("160") @QueryParam("wingChord") int wingChord,
            @DefaultValue("3") @QueryParam("attackAngle") double attackAngle,
            @DefaultValue("3") @QueryParam("dihedral") double dihedralAngle) {
        return new Ac3dFile(new ModelDataImpl(wingChord, wingSpan, dihedralAngle, attackAngle)).getAc3dFile();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("yasim")
    public YasimConfig getYasimFile(@Context HttpServletRequest httpServletRequest,
            @DefaultValue("800") @QueryParam("wingSpan") int wingSpan,
            @DefaultValue("160") @QueryParam("wingChord") int wingChord,
            @DefaultValue("3") @QueryParam("attackAngle") double attackAngle,
            @DefaultValue("3") @QueryParam("dihedral") double dihedralAngle) {
        return new YasimConfig(new ModelDataImpl(wingChord, wingSpan, dihedralAngle, attackAngle));
    }
}
