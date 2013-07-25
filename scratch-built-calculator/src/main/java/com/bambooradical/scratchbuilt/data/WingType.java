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

    flat("Flat", new double[][]{{1, 0}}),
    gurney("Gurney Flap", new double[][]{{1, 0}, {0.1, 90}}),
    // the following values are derived from the experiment three (makeAerofoilFoam3) in: https://github.com/PeterWithers/PrintableFlyables/blob/master/aerofoil-foam-board.scad
    hollow("Hollow", new double[][]{{0.96, 0.04}, {0.03, 20}, {0.04, 50}, {0.04, 50}, {0.08, 40}, {0.15, 16}, {0.25, 7}, {0.27, 7}});
    final private String label;
    final private double[][] lengthsAngles;

    private WingType(String label, double[][] lengthsAngles) {
        this.label = label;
        this.lengthsAngles = lengthsAngles;
    }

    public String getLabel() {
        return label;
    }

    public double[][] getLengths() {
        return lengthsAngles;
    }
}
