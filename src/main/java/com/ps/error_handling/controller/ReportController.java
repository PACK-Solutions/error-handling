package com.ps.error_handling.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ps.error_handling.model.Report;
import com.ps.error_handling.model.User;
import com.ps.error_handling.service.ReportService;
import com.ps.error_handling.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

/**
 * Controller for handling report-related requests. This controller provides endpoints for retrieving reports based on
 * user ID and report name.
 */
@RestController
@RequestMapping(
        value = "/v1",
        produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Report API", description = "API for retrieving reports")
@Validated
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;
    private final UserService userService;

    /**
     * Constructs a new ReportController with the specified services.
     *
     * @param reportService the service for retrieving reports
     * @param userService the service for retrieving users
     */
    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    /**
     * Retrieves a report for a specific user and report name.
     *
     * @param userId the ID of the user requesting the report
     * @param reportName the name of the report to retrieve
     * @return the requested report
     */
    @Operation(summary = "Get a report", description = "Retrieves a report for a specific user and report name")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Report found",
                        content =
                                @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = Report.class))),
                @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
                @ApiResponse(responseCode = "404", description = "User or report not found"),
                @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    @GetMapping("/report")
    public Report getReport(
            @Parameter(description = "ID of the user requesting the report")
                    @RequestParam
                    @NotBlank(message = "User ID cannot be empty")
                    String userId,
            @Parameter(description = "Name of the report to retrieve")
                    @RequestParam
                    @NotBlank(message = "Report name cannot be empty")
                    String reportName) {
        logger.info("Retrieving report for user {} and report name {}", userId, reportName);
        User user = userService.getUser(userId);
        return reportService.getReport(user.email(), reportName);
    }
}
