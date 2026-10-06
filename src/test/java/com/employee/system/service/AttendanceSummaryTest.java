```java
package com.employee.system.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AttendanceSummary}.
 * Since AttendanceSummary is a Lombok-generated POJO with @Data and @Builder,
 * we test the builder pattern, getters, setters, equals, hashCode, and toString.
 */
class AttendanceSummaryTest {

    @Test
    @DisplayName("Given valid attendance data, when building summary, then return summary with all fields set")
    void givenValidAttendanceData_whenBuildingSummary_thenReturnSummaryWithAllFieldsSet() {
        // Arrange
        Long employeeId = 1L;
        long totalDays = 30L;
        long presentDays = 25L;
        long absentDays = 2L;
        long lateDays = 1L;
        long halfDays = 1L;
        long leaveDays = 1L;

        // Act
        AttendanceSummary summary = AttendanceSummary.builder()
                .employeeId(employeeId)
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .halfDays(halfDays)
                .leaveDays(leaveDays)
                .build();

        // Assert
        assertNotNull(summary);
        assertEquals(employeeId, summary.getEmployeeId());
        assertEquals(totalDays, summary.getTotalDays());
        assertEquals(presentDays, summary.getPresentDays());
        assertEquals(absentDays, summary.getAbsentDays());
        assertEquals(lateDays, summary.getLateDays());
        assertEquals(halfDays, summary.getHalfDays());
        assertEquals(leaveDays, summary.getLeaveDays());
    }

    @Test
    @DisplayName("Given null employeeId, when building summary, then return summary with null employeeId")
    void givenNullEmployeeId_whenBuildingSummary_thenReturnSummaryWithNullEmployeeId() {
        // Arrange
        long totalDays = 0L;
        long presentDays = 0L;
        long absentDays = 0L;
        long lateDays = 0L;
        long halfDays = 0L;
        long leaveDays = 0L;

        // Act
        AttendanceSummary summary = AttendanceSummary.builder()
                .employeeId(null)
                .totalDays(totalDays)
                .presentDays(presentDays)
                .absentDays(absentDays)
                .lateDays(lateDays)
                .halfDays(halfDays)
                .leaveDays(leaveDays)
                .build();

        // Assert
        assertNotNull(summary);
        assertNull(summary.getEmployeeId());
        assertEquals(totalDays, summary.getTotalDays());
        assertEquals(presentDays, summary.getPresentDays());
        assertEquals(absentDays, summary.getAbsentDays());
        assertEquals(lateDays, summary.getLateDays());
        assertEquals(halfDays, summary.getHalfDays());
        assertEquals(leaveDays, summary.getLeaveDays());
    }

    @Test
    @DisplayName("Given zero values for all days, when building summary, then return summary with zero day counts")
    void givenZeroValuesForAllDays_whenBuildingSummary_thenReturnSummaryWithZeroDayCounts() {
        // Arrange
        Long employeeId = 100L;

        // Act
        AttendanceSummary summary = AttendanceSummary.builder()
                .employeeId(employeeId)
                .totalDays(0L)
                .presentDays(0L)
                .absentDays(0L)
                .lateDays(0L)
                .halfDays(0L)