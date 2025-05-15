package com.ps.error_handling.external;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Component;

import com.ps.error_handling.external.exception.InvalidReportNameException;
import com.ps.error_handling.external.exception.ReportForUserNotFoundException;
import com.ps.error_handling.model.Report;

@Component
public class ReportClient {

    /**
     * Retrieves a report for a given user email and report name.
     *
     * @param email The email address of the user requesting the report
     * @param reportName The name of the requested report
     * @return Report object containing the generated report data
     * @throws InvalidReportNameException if the report name is invalid
     * @throws ReportForUserNotFoundException if no report is found for the user
     */
    public Report getReport(String email, String reportName) {
        return switch (ThreadLocalRandom.current().nextInt(0, 10)) {
            case 0 -> throw new InvalidReportNameException();
            case 1 -> throw new ReportForUserNotFoundException();
            default -> new Report("someReportValue for " + email + " and " + reportName);
        };
    }
}
