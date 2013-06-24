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
                updateModel();
            }
            function getRestUrl(formatType) {
                var wingSpan = document.getElementById("wingspan").value;
                var wingChord = document.getElementById("wingchord").value;
                var dihedral = document.getElementById("dihedral").value;
                var attackangle = document.getElementById("attackangle").value;
                var getParam = '?wingSpan=' + wingSpan + '&wingChord=' + wingChord
                        + '&dihedral=' + dihedral + '&attackAngle=' + attackangle;
                if (typeof _gaq !== 'undefined')
                    _gaq.push(["_trackEvent", formatType, "view", "parameters", getParam]);
                return "./scratchbuilt/calculator/" + formatType + getParam;
            }
            function onLoaded() {
            }
            function updateModel() {
                document.getElementById("svgImage").src = getRestUrl('svg');
                viewer.show(getRestUrl('ac3d'), {callback: onLoaded});
            }</script>
        <script>
            (function(i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function() {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-41988110-1', 'scratch-built.appspot.com');
            ga('send', 'pageview');
        </script>
    </head>
    <body onload="onLoad();">
        <h1>Scratch Built R/C Calculator Prototype</h1>
        This is a work in progress, for more info: <a href="https://github.com/PeterWithers/ScratchBuiltCalculator">https://github.com/PeterWithers/ScratchBuiltCalculator</a><br>
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
                                <input id="clickMe" type="button" value="update" onclick="updateModel();" />
                            </td></tr></table>
                    <br>
                    <br>
                </td><td>
                    <canvas id="ac3dcanvas" height="500" width="900"></canvas>
                    <!--                    <embed id="svgImage" src="" type="image/svg+xml"/>-->
                    <iframe id="svgImage" height="500" width="900"></iframe>
                </td><td>
                    <input id="ac3dButton" type="button" value="Browse to AC3D File" onclick="window.location.href = getRestUrl('ac3d');"/><br>
                    <input id="svgButton" type="button" value="Browse to SVG File" onclick="window.location.href = getRestUrl('svg');"/><br>
                    <input id="yasimButton" type="button" value="Browse to YASim File" onclick="window.location.href = getRestUrl('yasim');"/>
                </td></tr></table>
    </body>
</html>
