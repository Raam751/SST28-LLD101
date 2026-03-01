public class LightsPanel implements BrightnessControllable, Switchable {

    public void powerOn() {
        /* ok */
    }

    public void powerOff() {
        System.out.println("Lights OFF");
    }

    public void setBrightness(int pct) {
        System.out.println("Lights set to " + pct + "%");
    }
}
