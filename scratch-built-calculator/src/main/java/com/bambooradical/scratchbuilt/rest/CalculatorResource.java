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
import com.bambooradical.scratchbuilt.data.WingType;
import com.bambooradical.scratchbuilt.serialisers.Ac3dFile;
import com.bambooradical.scratchbuilt.serialisers.AircraftAnimation;
import com.bambooradical.scratchbuilt.serialisers.AircraftSet;
import com.bambooradical.scratchbuilt.serialisers.SvgLayout;
import com.bambooradical.scratchbuilt.serialisers.YasimConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Created on : Jun 22, 2013, 5:06:33 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
@Path("/calculator")
public class CalculatorResource {

    @DefaultValue("flat")
    @QueryParam("wingType")
    WingType wingType;
    @DefaultValue("800")
    @QueryParam("wingSpan")
    int wingSpan;
    @DefaultValue("160")
    @QueryParam("wingChord")
    int wingChord;
    @DefaultValue("3")
    @QueryParam("attackAngle")
    double attackAngle;
    @DefaultValue("3")
    @QueryParam("dihedral")
    double dihedralAngle;
    @DefaultValue("395")
    @QueryParam("aileronEnd")
    int aileronEnd;
    @DefaultValue("200")
    @QueryParam("aileronStart")
    int aileronStart;
    @DefaultValue("40")
    @QueryParam("aileronChord")
    int aileronChord;
    @DefaultValue("15")
    @QueryParam("wingHeight")
    int wingHeight;
    @DefaultValue("30")
    @QueryParam("fuselageWidth")
    int fuselageWidth;
    @DefaultValue("30")
    @QueryParam("fuselageHeight")
    int fuselageHeight;
    @DefaultValue("15")
    @QueryParam("fuselageEndsDiameter")
    int fuselageEndsDiameter;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("ac3d")
    public String getAc3dFile(@Context HttpServletRequest httpServletRequest) {
        return new Ac3dFile(new ModelDataImpl(wingType, wingChord, wingSpan, dihedralAngle, attackAngle, aileronEnd, aileronStart, aileronChord, wingHeight, fuselageHeight, fuselageWidth, fuselageEndsDiameter)).getAc3dFile();
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("yasim")
    public YasimConfig getYasimFile(@Context HttpServletRequest httpServletRequest) {
        return new YasimConfig(new ModelDataImpl(wingType, wingChord, wingSpan, dihedralAngle, attackAngle, aileronEnd, aileronStart, aileronChord, wingHeight, fuselageHeight, fuselageWidth, fuselageEndsDiameter));
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("svg")
    public SvgLayout getSvgFile(@Context HttpServletRequest httpServletRequest) {
        return new SvgLayout(new ModelDataImpl(wingType, wingChord, wingSpan, dihedralAngle, attackAngle, aileronEnd, aileronStart, aileronChord, wingHeight, fuselageHeight, fuselageWidth, fuselageEndsDiameter));
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("set")
    public AircraftSet getSetFile(@Context HttpServletRequest httpServletRequest) {
        return new AircraftSet(new ModelDataImpl(wingType, wingChord, wingSpan, dihedralAngle, attackAngle, aileronEnd, aileronStart, aileronChord, wingHeight, fuselageHeight, fuselageWidth, fuselageEndsDiameter));
    }

    @GET
    @Produces("application/zip")
    @Path("zip")
    public Response getZipFile(@Context HttpServletRequest httpServletRequest) throws IOException, JAXBException {
        final ModelDataImpl modelDataImpl = new ModelDataImpl(wingType, wingChord, wingSpan, dihedralAngle, attackAngle, aileronEnd, aileronStart, aileronChord, wingHeight, fuselageHeight, fuselageWidth, fuselageEndsDiameter);
        StreamingOutput zipfileStream = new StreamingOutput() {
            @Override
            public void write(OutputStream out) throws IOException, WebApplicationException {
                try {
                    final AircraftSet aircraftSet = new AircraftSet(modelDataImpl);
                    final AircraftAnimation aircraftAnimation = new AircraftAnimation(modelDataImpl);

                    ZipOutputStream zipfile = new ZipOutputStream(out);
                    JAXBContext jaxbContext = JAXBContext.newInstance(YasimConfig.class, AircraftAnimation.class, AircraftSet.class);
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                    ZipEntry zipentry = new ZipEntry(aircraftSet.getSim().getAircraftAero() + ".xml");
                    zipfile.putNextEntry(zipentry);
                    marshaller.marshal(new YasimConfig(modelDataImpl), zipfile);

                    ZipEntry zipentryModel = new ZipEntry(aircraftSet.getSim().getAircraftModel().getPath());
                    zipfile.putNextEntry(zipentryModel);
                    marshaller.marshal(aircraftAnimation, zipfile);

                    ZipEntry zipentryAc = new ZipEntry(aircraftAnimation.getAcPath());
                    zipfile.putNextEntry(zipentryAc);
                    zipfile.write(new Ac3dFile(modelDataImpl).getAc3dFile().getBytes());

                    ZipEntry zipentrySet = new ZipEntry("scratch-built-set.xml");
                    zipfile.putNextEntry(zipentrySet);
                    marshaller.marshal(aircraftSet, zipfile);

                    zipfile.flush();
                    zipfile.close();
                } catch (IOException exception) {
                    throw new WebApplicationException(exception);
                } catch (JAXBException exception) {
                    throw new WebApplicationException(exception);
                }
            }
        };
        return Response.ok().entity(zipfileStream).header("Content-Disposition", "attachment; filename = scratchbuilt.zip").build();
    }
}
