import java.util.List;

public class BillingCalculator {
    public static double calculateTotal(List<OrderLine> items, String customerType,
            java.util.Map<String, MenuItem> menu, TaxCalculator taxCalc, DiscountCalculator discountCalc) {
        double subtotal = 0;
        for (OrderLine line : items) {
            MenuItem item = menu.get(line.itemId);
            subtotal += item.price * line.qty;
        }

        double taxPct = taxCalc.calculateTaxPct(customerType);
        double tax = subtotal * (taxPct / 100.0);

        double discount = discountCalc.calculateDiscount(customerType, subtotal, items.size());

        return subtotal + tax - discount;
    }
}
