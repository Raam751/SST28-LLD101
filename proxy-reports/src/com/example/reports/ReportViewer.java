package com.example.reports;

/**
 * Viewer depends only on the Report interface.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}