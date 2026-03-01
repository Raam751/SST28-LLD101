public class Demo10 {
    public static void main(String[] args) {
        System.out.println("=== Transport Booking ===");
        TripRequest req = new TripRequest("23BCS1010", new GeoPoint(12.97, 77.59), new GeoPoint(12.93, 77.62));
        Payment payment = new PaymentGateway();
        Allocator allocator = new DriverAllocator();
        DistanceService distanceService = new DistanceCalculator();

        new TransportBookingService(distanceService, allocator, payment).book(req);

        // Test mode: swap in mocks without touching TransportBookingService!
        System.out.println("\n=== Test Booking (Mocks) ===");
        Allocator mockAlloc = new MockAllocator();
        Payment mockPay = new MockPayment();
        new TransportBookingService(distanceService, mockAlloc, mockPay).book(req);

    }
}
