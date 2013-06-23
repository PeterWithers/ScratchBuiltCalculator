<%--

    Copyright (C) 2013 Peter Withers

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public License
    as published by the Free Software Foundation; either version 2
    of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

--%>
<%@page import="com.bambooradical.scratchbuilt.data.TrainerData"%>
<%-- 
    Document   : scratchbuiltcalculator
    Created on : Jun 22, 2013, 5:00:13 PM
    Author     : Peter Withers <peter-ghc@bambooradical.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Scratch Built R/C Calculator Prototype</title>
        <script type="text/javascript" src="hangar-read-only/src/libs/gl-matrix-min.js"></script> 
        <script type="text/javascript" src="hangar-read-only/src/libs/polyfill.js"></script>                 
        <script type="text/javascript" src="hangar-read-only/src/ac.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/hangar.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/camera.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/loader.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/renderer.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/scene.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/sgi.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/shader.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/trackball.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/triangulator.js"></script>
        <script type="text/javascript" src="hangar-read-only/src/viewer.js"></script>
        <script type="text/javascript">
            var canvas;

            function onLoad() {
                canvas = document.getElementById("ac3dcanvas");
                viewer = new HG.Viewer(canvas);

                viewer.show(getRestUrl('ac3d'), {callback: onLoaded});
            }
            function getRestUrl(formatType) {
                var wingSpan = document.getElementById("wingspan").value;
                var wingChord = document.getElementById("wingchord").value;
                var dihedral = document.getElementById("dihedral").value;
                var attackangle = document.getElementById("attackangle").value;
                return './scratchbuilt/calculator/' + formatType + '?wingSpan=' + wingSpan + '&wingChord=' + wingChord
                        + '&dihedral=' + dihedral + '&attackAngle=' + attackangle;
            }
            function onLoaded() {
            }</script>
    </head>
    <body onload="onLoad();">
        <h1>Scratch Built R/C Calculator Prototype</h1>
        <table><tr><td>
                    <%--<%= // TrainerData trainerData = new TrainerData().getWingSpan(); %>--%>
                    <table><tr><td>
                                Wing&nbsp;Span
                            </td><td>
                                <input id="wingspan" value="<%= new TrainerData().getWingSpan()%>"/>
                            </td></tr><tr><td>
                                Wing&nbsp;Chord
                            </td><td>
                                <input id="wingchord" value="<%= new TrainerData().getChordLength()%>"/>
                            </td></tr><tr><td>
                                Dihedral&nbsp;Angle
                            </td><td>
                                <input id="dihedral" value="<%= new TrainerData().getDihedralAngle()%>"/>
                            </td></tr><tr><td>
                                Attack&nbsp;Angle
                            </td><td>
                                <input id="attackangle" value="<%= new TrainerData().getAttackAngle()%>"/>                            
                            </td></tr><tr><td></td><td>
                                <input id="clickMe" type="button" value="update" onclick="viewer.show(getRestUrl('ac3d'), {callback: onLoaded});" />
                            </td></tr></table>
                    <br>
                    <br>
                </td><td>
                    <canvas id="ac3dcanvas" height="500" width="900"></canvas>
                </td><td>
                    <input id="ac3dButton" type="button" value="Browse to AC3D File" onclick="window.location.href = getRestUrl('ac3d');"/><br>
                    <input id="yasimButton" type="button" value="Browse to YASim File" onclick="window.location.href = getRestUrl('yasim');"/>
                </td></tr></table>
    </body>
</html>
