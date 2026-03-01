// If Exporter
// was an interface, then 
// PdfExporter and CsvExporter
// would each have to write their own 
//export() method from scratch. If they wrote it from scratch, 
// they could easily "forget" to check for nulls,
// or they could throw random exceptions again, breaking LSP.

public abstract class Exporter {

    private DeliveryConstraint constraint;

    public void setConstraint(DeliveryConstraint constraint) {
        this.constraint = constraint;
    }

    public final ExportResult export(ExportRequest req) {
        if (req == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (req.title == null || req.title.isBlank()) {
            throw new IllegalArgumentException("Request title cannot be null or blank");
        }

        if (this.constraint != null) {
            this.constraint.check(req);
        }

        ExportResult result = encode(req);

        if (result == null) {
            throw new IllegalStateException("Encode returned a null result");
        }
        return result;
    }

    public abstract ExportResult encode(ExportRequest req);
}
