# Audio Control System A discrete transistor audio control system built without any ICs.
 ## Description The system takes a control signal of 120 sin(4t) and uses it to gate two audio 
speakers at different frequency ranges. - Speaker 1 (Low-Mid ~300 Hz) speaks during periods 1 
to 3 when the control signal amplitude exceeds 40V - Speaker 2 (Upper-Mid ~2 kHz) speaks 
during periods 4 to 5 when the control signal amplitude exceeds 80V - The system stops 
completely after 30 periods ## Components Used - 2N2222 NPN transistors (comparators, AND gates, timers, switches)
 - 2N2907 PNP transistor (relay driver) - RC timing circuits (no 555 timer, no ICs)
 - LC Hartley oscillators - Electromechanical relay for STOP function 
## Files - Proteus/ — schematic and PCB simulation files - Altium/ — schematic and PCB layout files 
- Images/ — screenshots of schematic and PCB ## Tools Used - Proteus 8 Professional - Altium Designer