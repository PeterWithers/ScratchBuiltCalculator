G162 Z F1000; home Z axes maximum
G92 X0 Y0 Z-5 A0 B0; set Z to -5
G1 Z0.0 F1000; move Z to '0'
G162 Z F300; home Z axes maximum
G161 X Y F2500; home XY axis minimum
M132 X Y Z A B; recall stored home offsets for XYZAB axis
G1 X25 Y-60 Z10 F3300.0; move to waiting position
M109 S110 T0; set platform temperature
M134 T0; wait for platform to be ready
M135 T0; change tool
M104 S230 T0; set extruder temperature
M133 T0; wait for tool to be ready
; end start gcode