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

/**
 * Created on : Jun 22, 2013, 5:21:26 PM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class Ac3dFile {

    final private ModelData modelData;

    public Ac3dFile(ModelData modelData) {
        this.modelData = modelData;
    }
// http://www.inivis.com/ac3d/man/ac3dfileformat.html

    public String getAc3dFile() {
        return "AC3Db\n"
                + "MATERIAL \"\" rgb 1 1 1  amb 0.2 0.2 0.2  emis 0 0 0  spec 0.5 0.5 0.5  shi 10  trans 0\n"
                + "OBJECT world\n"
                + "kids 1\n"
                + "OBJECT poly\n"
                + "name \"rect\"\n"
                + "loc 1 0.5 0\n"
                + "numvert 4\n"
                + "-1 0.5 0\n"
                + "1 0.5 0\n"
                + "1 -0.5 0\n"
                + "-1 -0.5 0\n"
                + "numsurf 1\n"
                + "SURF 0x20\n"
                + "mat 0\n"
                + "refs 4\n"
                + "3 0 0\n"
                + "2 1 0\n"
                + "1 1 1\n"
                + "0 0 1\n"
                + "kids 0";
    }
}
