public class MockPayment implements Payment {
    @Override
    public String charge(String studentId, double amount) {
        return "MOCK-TXN-0000";
    }
}
