package com.ps.error_handling.service;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ps.error_handling.external.ReportClient;
import com.ps.error_handling.external.exception.InvalidReportNameException;
import com.ps.error_handling.external.exception.ReportForUserNotFoundException;
import com.ps.error_handling.model.Report;
import com.ps.error_handling.service.exception.ReportClientUnexpectedException;
import com.ps.error_handling.service.exception.ReportNameNotFoundInReportApiException;
import com.ps.error_handling.service.exception.UserNotFoundInReportApiException;

/**
 * Service for handling report-related operations. This service provides methods for retrieving reports using the
 * external report client.
 */
@Service
public class ReportService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    private final ReportClient reportClient;

    /**
     * Constructs a new ReportService with the specified client.
     *
     * @param reportClient the client for accessing external report data
     */
    public ReportService(ReportClient reportClient) {
        this.reportClient = reportClient;
    }

    /**
     * Retrieves a report for a specific user email and report name.
     *
     * @param email the email of the user requesting the report
     * @param reportName the name of the report to retrieve
     * @return the requested report
     * @throws IllegalArgumentException if email or reportName is null or empty, or if email format is invalid
     * @throws ReportNameNotFoundInReportApiException if the report name is invalid
     * @throws UserNotFoundInReportApiException if no report is found for the user
     * @throws ReportClientUnexpectedException if there is an unexpected error from the report client
     */
    public Report getReport(String email, String reportName) {
        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (!StringUtils.hasText(reportName)) {
            throw new IllegalArgumentException("Report name cannot be null or empty");
        }

        try {
            return reportClient.getReport(email, reportName);
        } catch (InvalidReportNameException e) {
            throw new ReportNameNotFoundInReportApiException();
        } catch (ReportForUserNotFoundException e) {
            throw new UserNotFoundInReportApiException();
        } catch (Exception e) {
            throw new ReportClientUnexpectedException(e);
        }
    }
}
