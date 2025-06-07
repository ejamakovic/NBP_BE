package com.NBP.NBP.schedulers;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.NBP.NBP.services.reports.ReportService;

@Component
public class ReportScheduler {

    private final ReportService reportService;

    public ReportScheduler(ReportService reportService) {
        this.reportService = reportService;
    }

    // Every Monday at 08:00 AM
    @Scheduled(cron = "0 0 8 * * MON")
    public void sendWeeklyReportToAdmins() {
        boolean success = reportService.generateAndSendReportToAdmins();
        if (success) {
            System.out.println("[✓] Weekly report sent to admins.");
        } else {
            System.err.println("[✗] Failed to send weekly report.");
        }
    }
}
