import java.util.*;

public class Demo04 {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        List<FeeComponent> feeComponents = List.of(
                new RoomFee(LegacyRoomTypes.SINGLE, 14000.0),
                new RoomFee(LegacyRoomTypes.DOUBLE, 15000.0),
                new RoomFee(LegacyRoomTypes.TRIPLE, 12000.0),
                new RoomFee(LegacyRoomTypes.DELUXE, 16000.0),
                new AddOnFee(AddOn.MESS, 1000.0),
                new AddOnFee(AddOn.LAUNDRY, 500.0),
                new AddOnFee(AddOn.LATEFEE, 100.0),
                new AddOnFee(AddOn.GYM, 300.0));

        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), feeComponents);
        calc.process(req);
    }
}
