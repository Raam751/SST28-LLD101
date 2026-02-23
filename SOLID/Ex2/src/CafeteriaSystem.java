import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceRepository store;
    private final TaxCalculator taxCalc;
    private final DiscountCalculator discountCalc;
    private int invoiceSeq = 1000;

    public CafeteriaSystem(InvoiceRepository store, TaxCalculator taxCalc, DiscountCalculator discountCalc) {
        this.store = store;
        this.taxCalc = taxCalc;
        this.discountCalc = discountCalc;
    }

    public void addToMenu(MenuItem i) {
        menu.put(i.id, i);
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        String invId = "INV-" + (++invoiceSeq);
        // Calculate billing data
        double total = BillingCalculator.calculateTotal(lines, customerType, menu, taxCalc, discountCalc);
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            subtotal += item.price * l.qty;
        }
        double taxPct = taxCalc.calculateTaxPct(customerType);
        double tax = subtotal * (taxPct / 100.0);
        double discount = discountCalc.calculateDiscount(customerType, subtotal, lines.size());

        // Create ReceiptData and format
        ReceiptData receiptData = new ReceiptData(invId, lines, subtotal, taxPct, tax, discount, total, menu);
        String printable = InvoiceFormatter.format(receiptData);

        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
