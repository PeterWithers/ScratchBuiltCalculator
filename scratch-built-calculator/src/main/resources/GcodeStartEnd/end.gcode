; start end gcode
G1 X0 Y55 F3300; move the platform forward
G162 Z F1000; home Z axis maximum
M109 S0 T0; turn off platform heater
M104 S0 T0; turn off extruder heater
M18 A B; turn off A and B stepper motors
M18 X Y Z; turn off X Y Z stepper motors