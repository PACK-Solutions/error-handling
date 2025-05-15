package com.ps.error_handling.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ps.error_handling.model.Report;
import com.ps.error_handling.model.User;
import com.ps.error_handling.service.ReportService;
import com.ps.error_handling.service.UserService;
import com.ps.error_handling.service.exception.ReportNameNotFoundInReportApiException;
import com.ps.error_handling.service.exception.UserNotFoundInDbException;
import com.ps.error_handling.service.exception.UserNotFoundInReportApiException;

@WebMvcTest(ReportController.class)
@Import(ReportControllerTest.TestConfig.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    // Test configuration to provide mock beans
    static class TestConfig {
        @Bean
        @Primary
        public ReportService reportService() {
            return Mockito.mock(ReportService.class);
        }

        @Bean
        @Primary
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    private static final String VALID_USER_ID = "user123";
    private static final String VALID_EMAIL = "user@example.com";
    private static final String VALID_REPORT_NAME = "sales-report";
    private static final String REPORT_VALUE = "Report data";

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        Mockito.reset(userService, reportService);
    }

    @Test
    void getReport_WithValidParameters_ReturnsReport() throws Exception {
        // Set up mocks for this test
        User user = new User(VALID_USER_ID, VALID_EMAIL);
        Report report = new Report(REPORT_VALUE);

        when(userService.getUser(VALID_USER_ID)).thenReturn(user);
        when(reportService.getReport(VALID_EMAIL, VALID_REPORT_NAME)).thenReturn(report);

        mockMvc.perform(get("/v1/report")
                        .param("userId", VALID_USER_ID)
                        .param("reportName", VALID_REPORT_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reportValue").value(REPORT_VALUE));
    }

    @Test
    void getReport_WithMissingUserId_ReturnsBadRequest() throws Exception {
        // No need to set up mocks for this test as it fails at validation level
        mockMvc.perform(get("/v1/report").param("reportName", VALID_REPORT_NAME).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReport_WithMissingReportName_ReturnsBadRequest() throws Exception {
        // No need to set up mocks for this test as it fails at validation level
        mockMvc.perform(get("/v1/report").param("userId", VALID_USER_ID).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReport_WithEmptyUserId_ReturnsBadRequest() throws Exception {
        // No need to set up mocks for this test as it fails at validation level
        mockMvc.perform(get("/v1/report")
                        .param("userId", "")
                        .param("reportName", VALID_REPORT_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReport_WithEmptyReportName_ReturnsBadRequest() throws Exception {
        // No need to set up mocks for this test as it fails at validation level
        mockMvc.perform(get("/v1/report")
                        .param("userId", VALID_USER_ID)
                        .param("reportName", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReport_WithNonExistentUser_ReturnsNotFound() throws Exception {
        // Set up mock to throw exception when a non-existent user is requested
        when(userService.getUser("nonexistent")).thenThrow(new UserNotFoundInDbException("nonexistent"));

        mockMvc.perform(get("/v1/report")
                        .param("userId", "nonexistent")
                        .param("reportName", VALID_REPORT_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Provided user id doesn't exist."));
    }

    @Test
    void getReport_WithInvalidReportName_ReturnsBadRequest() throws Exception {
        // Set up mocks for this test
        User user = new User(VALID_USER_ID, VALID_EMAIL);
        when(userService.getUser(VALID_USER_ID)).thenReturn(user);
        when(reportService.getReport(VALID_EMAIL, "invalid-report"))
                .thenThrow(new ReportNameNotFoundInReportApiException());

        mockMvc.perform(get("/v1/report")
                        .param("userId", VALID_USER_ID)
                        .param("reportName", "invalid-report")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid Report name."));
    }

    @Test
    void getReport_WithUserNotFoundInReportApi_ReturnsNotFound() throws Exception {
        // Set up mocks for this test
        User user = new User(VALID_USER_ID, VALID_EMAIL);
        when(userService.getUser(VALID_USER_ID)).thenReturn(user);
        when(reportService.getReport(VALID_EMAIL, VALID_REPORT_NAME)).thenThrow(new UserNotFoundInReportApiException());

        mockMvc.perform(get("/v1/report")
                        .param("userId", VALID_USER_ID)
                        .param("reportName", VALID_REPORT_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Report for given user not found."));
    }

    @Test
    void getReport_WithInvalidEmail_ReturnsBadRequest() throws Exception {
        // Set up mocks for this test
        String invalidUserId = "user-invalid-email";
        String invalidEmail = "invalid-email";
        User userWithInvalidEmail = new User(invalidUserId, invalidEmail);

        when(userService.getUser(invalidUserId)).thenReturn(userWithInvalidEmail);
        when(reportService.getReport(invalidEmail, VALID_REPORT_NAME))
                .thenThrow(new IllegalArgumentException("Invalid email format"));

        mockMvc.perform(get("/v1/report")
                        .param("userId", invalidUserId)
                        .param("reportName", VALID_REPORT_NAME)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Invalid argument: Invalid email format"));
    }
}
