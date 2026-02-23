public class RoomFee implements FeeComponent {
    private final int roomType;
    private final double basePrice;

    public RoomFee(int roomType, double basePrice) {
        this.roomType = roomType;
        this.basePrice = basePrice;
    }

    @Override
    public double calculateFee(BookingRequest req) {
        if (req.roomType == this.roomType) {
            return basePrice;
        }
        return 0.0;
    }
}
