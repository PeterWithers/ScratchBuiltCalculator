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

import com.bambooradical.scratchbuilt.data.Colour;
import com.bambooradical.scratchbuilt.data.TrainerData;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Peter Withers <peter-ghc@bambooradical.com>
 */
public class Ac3dFileTest {

    public Ac3dFileTest() {
    }

    /**
     * Test of getFormattedColour method, of class Ac3dFile.
     */
    @Test
    public void testGetFormattedColour() {
        System.out.println("getFormattedColour");
        Colour colour = null;
        Ac3dFile instance = new Ac3dFile(null);
        String expResult0 = "0.00 0.00 0.00";
        String result0 = instance.getFormattedColour(Colour.BLACK);
        assertEquals(expResult0, result0);
        String expResult1 = "1.00 1.00 1.00";
        String result1 = instance.getFormattedColour(Colour.WHITE);
        assertEquals(expResult1, result1);
        String expResult2 = "0.50 0.50 0.50";
        String result2 = instance.getFormattedColour(Colour.GREY);
        assertEquals(expResult2, result2);
    }

    private String getLine(Scanner scanner) {
        final String nextLine = scanner.next();
        System.out.println(nextLine);
        return nextLine;
    }

    private void checkKids(Scanner scanner, int kidCount, String current) {
        int foundCount = 0;
        while (current != null) {
            assertTrue(current.startsWith("OBJECT"));
            foundCount++;
            current = getLine(scanner);
            for (String optional : new String[]{"name", "data", "texture", "texrep", "rot", "loc", "url"}) {
                if (current.startsWith(optional)) {
                    current = getLine(scanner);
                }
            }
            if (current.startsWith("numvert")) {
                int vertCount = Integer.parseInt(current.split(" ")[1]);
                current = getLine(scanner);
                int actualVert = 0;
                // todo: test the count of "the numvert lines of "
                while (current.matches("[0-9\\.\\- ]*")) {
                    current = getLine(scanner);
                    actualVert++;
                }
                assertEquals(vertCount, actualVert);
            }
            if (current.startsWith("numsurf")) {
                int surfCount = Integer.parseInt(current.split(" ")[1]);
                current = getLine(scanner);
                int actualSurf = 0;
                while (current.startsWith("SURF")) {
                    actualSurf++;
                    current = getLine(scanner);
                    for (String optional : new String[]{"mat"}) {
                        if (current.startsWith(optional)) {
                            current = getLine(scanner);
                        }
                    }
                    assertTrue(current.startsWith("refs"));
                    int refsCount = Integer.parseInt(current.split(" ")[1]);
                    current = getLine(scanner);
                    int actualRefs = 0;
                    // todo: test the count of "the refs lines of "
                    while (current.matches("[0-9\\.\\- ]*")) {//"-?0-9+\\.?-?0-9* -?0-9+\\.?-?0-9* -?0-9+\\.?-?0-9*")) {
                        actualRefs++;
                        current = getLine(scanner);
                    }
                    assertEquals(refsCount, actualRefs);
                }
                assertEquals(surfCount, actualSurf);
            }
            assertTrue(current.startsWith("kids"));
            int nextKidsCount = Integer.parseInt(current.split(" ")[1]);
            if (nextKidsCount > 0) {
                current = getLine(scanner);
                checkKids(scanner, nextKidsCount, current);
            } else if (foundCount < kidCount) {
                if (scanner.hasNext()) {
                    current = getLine(scanner);
                } else {
                    current = null;
                }
            } else {
                return;
            }
        }
        assertEquals(foundCount, kidCount);
    }

    /**
     * Test of getAc3dFile method, of class Ac3dFile.
     *
     * The output should match the following format description from
     * http://www.inivis.com/ac3d/man/ac3dfileformat.html
     */
    //MATERIAL %s rgb %f %f %f  amb %f %f %f  emis %f %f %f  spec %f %f %f  shi %d  trans %f
    //OBJECT %s
    //	*name %s
    //        *data %d
    //	*texture %s
    //	*texrep %f %f
    //	*rot %f %f %f  %f %f %f  %f %f %f
    //	*loc %f %f %f
    //	*url %s
    //	*numvert %d
    //		numvert lines of %f %f %f
    //	*numsurf %d
    //		*SURF %d
    //		*mat %d
    //		refs %d
    //                refs lines of %d %f %f
    //	kids %d
    @Test
    public void testGetAc3dFile() {
        System.out.println("getAc3dFile");
        Ac3dFile instance = new Ac3dFile(new TrainerData());
        String result = instance.getAc3dFile();
        Scanner scanner = new Scanner(result);
        scanner.useDelimiter("\n");
        assertEquals("AC3Db", scanner.next());
        String current = getLine(scanner);
        while (current.startsWith("MATERIAL")) {
            current = getLine(scanner);
        }
        checkKids(scanner, 1, current);
        scanner.close();
    }
}