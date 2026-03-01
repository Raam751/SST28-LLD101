public class Projector implements Switchable, InputConnectable, Scannable {
    private boolean on;

    public void powerOn() {
        on = true;
    }

    public void powerOff() {
        on = false;
        System.out.println("Projector OFF");
    }

    public int scanAttendance() {
        return 0;
    } // dummy

    public void connectInput(String port) {
        if (on)
            System.out.println("Projector ON (" + port + ")");
    }
}
