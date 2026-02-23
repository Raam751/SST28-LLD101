public class AddOnFee implements FeeComponent {
    private final AddOn addOn;
    private final double price;

    public AddOnFee(AddOn addOn, double price) {
        this.addOn = addOn;
        this.price = price;
    }

    @Override
    public double calculateFee(BookingRequest req) {
        if (req.addOns.contains(this.addOn)) {
            return price;
        }
        return 0.0;
    }
}
