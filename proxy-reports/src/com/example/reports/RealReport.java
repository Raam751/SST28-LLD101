package com.example.reports;

/**
 * The real subject — does the expensive disk loading.
 */
public class RealReport implements Report {

    private final String reportId;
    private final String title;
    private final String classification;

    public RealReport(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
        System.out.println("[disk] loading report " + reportId + " ...");
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void display(User user) {
        String content = "Internal report body for " + title;
        System.out.println("REPORT -> id=" + reportId
                + " title=" + title
                + " classification=" + classification
                + " openedBy=" + user.getName());
        System.out.println("CONTENT: " + content);
    }

    public String getClassification() {
        return classification;
    }
}