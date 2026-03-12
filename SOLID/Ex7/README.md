# Ex7 — ISP: Smart Classroom Devices Interface

## 1. Context
A smart classroom controller manages devices: projector, lights, AC, attendance scanner.

## 2. Current behavior
- There is one large interface `SmartClassroomDevice` containing many methods
- Each device implements methods it does not need using dummy logic
- Controller calls only some methods depending on device type

## 3. What’s wrong (at least 5 issues)
1. Fat interface forces irrelevant methods on devices.
2. Dummy implementations hide bugs and create misleading behavior.
3. Controller is tempted to call methods that some devices don’t meaningfully support.
4. Adding a new device forces implementing many unrelated methods.
5. Device capabilities are unclear; interface does not model reality.

## 4. Your task
- Split the fat interface into smaller capability-based interfaces.
- Update controller and devices to depend only on what they use.
- Preserve console output.

## 5. Constraints
- Preserve output for `Main`.
- Keep device class names unchanged.
- No external libs.

## 6. Acceptance criteria
- No device implements methods irrelevant to it.
- Controller depends only on specific capability interfaces.

## 7. How to run
```bash
cd SOLID/Ex7/src
javac *.java
java Main
```

## 8. Sample output
```text
=== Smart Classroom ===
Projector ON (HDMI-1)
Lights set to 60%
AC set to 24C
Attendance scanned: present=3
Shutdown sequence:
Projector OFF
Lights OFF
AC OFF
```

## 9. Hints (OOP-only)
- Capabilities: power control, brightness control, temperature control, scanning.
- Keep composition: registry can return devices by capability rather than by concrete class.

## 10. Stretch goals
- Add a “smart board” device without implementing unrelated methods.

---



**Step 1 — State the problem:**
"This is about ISP — Interface Segregation Principle. We had one fat interface called SmartClassroomDevice with methods like powerOn(), powerOff(), setBrightness(), setTemperature(), scanAttendance(), connectInput(). Every device — Projector, Lights, AC, Scanner — had to implement ALL these methods even if they didn't support them."

**Step 2 — Show the problem with a concrete example:**
"An AirConditioner had to implement setBrightness() and connectInput() — things an AC can't do. These were dummy implementations that either did nothing or threw exceptions. That's dangerous because someone could accidentally call ac.setBrightness() and get silent failure."

**Step 3 — Show the fix:**
"I split the fat interface into small, capability-based interfaces: Switchable (powerOn/powerOff), BrightnessControllable (setBrightness), TemperatureControllable (setTemperature), Scannable (scanAttendance), InputConnectable (connectInput). Each device now implements only what it actually supports."

**Step 4 — Show a device (open Projector.java):**
"Projector implements Switchable, InputConnectable, and Scannable — exactly what a projector can do. AirConditioner implements only Switchable and TemperatureControllable. No dummy methods anywhere."

**Step 5 — Show DeviceRegistry:**
"The registry stores devices as Object and retrieves them by capability. When the controller needs all Switchable devices, it asks for that interface specifically. This means the controller depends only on the interfaces it needs."

**Step 6 — Key takeaway:**
"ISP says no client should be forced to depend on methods it doesn't use. By splitting the fat interface, each device implements only relevant methods, and each client depends only on the capabilities it needs."
