public class Demo05 {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        pdf.setConstraint(r -> {
            if (r.body != null && r.body.length() > 20) {
                throw new IllegalArgumentException("PDF cannot handle content > 20 chars");
            }
        });

        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();
        Exporter xml = new XmlExporter();

        System.out.println("PDF: " + safe(pdf, req));
        System.out.println("CSV: " + safe(csv, req));
        System.out.println("JSON: " + safe(json, req));
        System.out.println("XML: " + safe(xml, req));
    }

    private static String safe(Exporter e, ExportRequest r) {
        try {
            ExportResult out = e.export(r);
            return "OK bytes=" + out.bytes.length;
        } catch (RuntimeException ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}
