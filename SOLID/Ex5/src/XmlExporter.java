import java.nio.charset.StandardCharsets;

public class XmlExporter extends Exporter {
    @Override
    protected ExportResult encode(ExportRequest req) {
        String xml = "<export>\n" +
                "  <title>" + escape(req.title) + "</title>\n" +
                "  <body>" + escape(req.body) + "</body>\n" +
                "</export>";
        return new ExportResult("application/xml", xml.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null)
            return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
