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

/**
 * Created on : Jul 20, 2013, 11:16 AM
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public enum WingType {

    flat("Flat", new double[]{1}, new double[]{0}),
    gurney("Gurney Flap", new double[]{1, 0.1}, new double[]{0, 90}),
    hollow("Hollow", new double[]{1, 0.2, 0.5}, new double[]{0, 90, 90});
    final private String label;
    final private double[] lengths;
    final private double[] angles;

    private WingType(String label, double[] lengths, double[] angles) {
        this.label = label;
        this.lengths = lengths;
        this.angles = angles;
    }

    public String getLabel() {
        return label;
}

    public double[] getLengths() {
        return lengths;
    }

    public double[] getAngles() {
        return angles;
    }
}
