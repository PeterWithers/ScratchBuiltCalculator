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
                canvas = document.getElementById("canvas");
                viewer = new HG.Viewer(canvas);

                //viewer.show('./scratchbuilt/calculator/ac3d', {callback: onLoaded});
            }

            function onLoaded() {
            }</script>
    </head>
    <body onload="onLoad();">
        <h1>Scratch Built R/C Calculator Prototype</h1>
        <a href="scratchbuilt/calculator/ac3d">test ac3d</a><br>
        <input id="clickMe" type="button" value="clickme" onclick="viewer.show('./scratchbuilt/calculator/ac3d', {callback: onLoaded});" /><br>
        <canvas id="canvas"></canvas><br>
    </body>
</html>
