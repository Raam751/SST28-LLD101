import java.util.*;

public class DeviceRegistry {
    private final java.util.List<Object> devices = new ArrayList<>();

    public void add(Object d) {
        devices.add(d);
    }

    public Object getFirstOfType(String simpleName) {
        for (Object d : devices) {
            if (d.getClass().getSimpleName().equals(simpleName))
                // d.getClass() -> asks Java "What is the actual class of this object?"
                // .getSimpleName() -> returns the name of that class as a String
                return d;
        }
        throw new IllegalStateException("Missing: " + simpleName);
    }
}
