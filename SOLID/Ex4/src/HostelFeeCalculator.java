import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final List<FeeComponent> feeComponents;

    public HostelFeeCalculator(FakeBookingRepo repo, List<FeeComponent> feeComponents) {
        this.repo = repo;
        this.feeComponents = feeComponents;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); // deterministic-ish
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        double total = 0.0;
        for (FeeComponent component : feeComponents) {
            total += component.calculateFee(req);
        }
        return new Money(total);
    }
}
