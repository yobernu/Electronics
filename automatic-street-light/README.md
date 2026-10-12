
##  Automatic Street Light Control System

A simulation-based prototype of an **automatic street light control system** 
built using **Proteus Design Suite**. The system detects ambient light 
intensity and automatically activates street lights during low-light 
conditions (night), with a timed operation sequence.

---

##  Project Overview

This project simulates a smart street lighting system that:
- **Automatically turns ON** street lights when darkness is detected
- Uses **light intensity detection** to trigger the lighting sequence
- Operates each light with a **30-minute ON timer** (simulated at 10 minutes for testing)
- Controls **5 street light stages** sequentially using transistor switching circuits

---

##  How It Works

1. A **light sensor (LDR)** detects the drop in ambient light intensity
2. The signal passes through **Zener diodes (D1–D5)** at different voltage thresholds
3. Each threshold triggers a **2N2369 NPN transistor (Q1–Q5)** to switch ON
4. The transistor drives a **green LED** representing a street light
5. Lights activate **sequentially** as light intensity decreases

---

##  Components Used

| Component | Value / Model | Quantity |
|---|---|---|
| NPN Transistor | 2N2369 | 5 |
| Zener Diodes | D1–D5 (various voltages) | 5 |
| Resistors (Base) | 1kΩ | 5 |
| Resistors (Bias) | 10kΩ | 5 |
| Resistors (LED) | 470Ω | 5 |
| LEDs | Green (D6–D10) | 5 |

---

##  Simulation Details

- **Software:** Proteus Design Suite
- **Simulation Timer:** 10 minutes (represents 30 minutes real-world)
- **Trigger:** Light intensity drop detected by sensor input

---

##  How to Run the Simulation

1. Install **Proteus Design Suite** (version 8 or higher)
2. Clone this repository:
```bash
   git clone https://github.com/Bangura30/automatic-street-light.git
```
3. Open the `.pdsprj` file in Proteus
4. Click **Play** to run the simulation
5. Observe LEDs activating sequentially as light intensity drops

---

##  Real-World Application

- Replace LEDs with high-power street lamps
- Use a real **LDR sensor** for light detection
- Operate on a **30-minute cycle**
- Saves energy by only activating lights when needed

---

##  Author

**Bangura30**
Electronics Engineer
🔗 [GitHub Profile](https://github.com/Bangura30)

---

##  License

This project is open-source under the **MIT License**
