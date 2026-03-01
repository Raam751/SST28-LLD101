public class DriverAllocator implements Allocator {
    public String allocate(String studentId) {
        // fake deterministic driver
        return "DRV-17";
    }
}
