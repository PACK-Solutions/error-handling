package com.ps.error_handling.service;

import com.ps.error_handling.external.ReportClient;
import com.ps.error_handling.external.exception.InvalidReportNameException;
import com.ps.error_handling.external.exception.ReportForUserNotFoundException;
import com.ps.error_handling.model.Report;
import com.ps.error_handling.service.exception.ReportClientUnexpectedException;
import com.ps.error_handling.service.exception.ReportNameNotFoundInReportApiException;
import com.ps.error_handling.service.exception.UserNotFoundInReportApiException;
import org.springframework.stereotype.Service;

/**
 * Service for handling report-related operations.
 * This service provides methods for retrieving reports using the external report client.
 */
@Service
public class ReportService {

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
     * @param email      the email of the user requesting the report
     * @param reportName the name of the report to retrieve
     * @return the requested report
     * @throws ReportNameNotFoundInReportApiException if the report name is invalid
     * @throws UserNotFoundInReportApiException       if no report is found for the user
     * @throws ReportClientUnexpectedException        if there is an unexpected error from the report client
     */
    public Report getReport(String email, String reportName) {
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
