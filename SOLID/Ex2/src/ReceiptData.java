import java.util.List;

public class ReceiptData {
    public final String invoiceId;
    public final List<OrderLine> items;
    public final double subtotal;
    public final double taxPct;
    public final double tax;
    public final double discount;
    public final double total;
    public final java.util.Map<String, MenuItem> menu;

    public ReceiptData(String invoiceId, List<OrderLine> items, double subtotal, double taxPct, double tax,
            double discount, double total, java.util.Map<String, MenuItem> menu) {
        this.invoiceId = invoiceId;
        this.items = items;
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.menu = menu;
    }
}
