public class MockAllocator implements Allocator {
    @Override
    public String allocate(String studentId) {
        return "MOCK-DRV-00";
    }
}
