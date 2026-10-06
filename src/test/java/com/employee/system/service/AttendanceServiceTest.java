```java
package com.employee.system.service;

import com.employee.system.dto.AttendanceDTO;
import com.employee.system.entity.Attendance;
import com.employee.system.entity.Employee;
import com.employee.system.repository.AttendanceRepository;
import com.employee.system.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private AttendanceService attendanceService;

    private Employee testEmployee;
    private Attendance testAttendance;
    private AttendanceDTO testAttendanceDTO;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");

        testDate = LocalDate.of(2023, 10, 15);

        testAttendance = new Attendance();
        testAttendance.setId(1L);
        testAttendance.setEmployee(testEmployee);
        testAttendance.setAttendanceDate(testDate);
        testAttendance.setStatus("PRESENT");
        testAttendance.setCheckInTime(LocalDateTime.of(2023, 10, 15, 9, 0, 0));
        testAttendance.setCheckOutTime(LocalDateTime.of(2023, 10, 15, 17, 0, 0));
        testAttendance.setRemarks("On time");
        testAttendance.setCreatedAt(LocalDateTime.now());
        testAttendance.setUpdatedAt(LocalDateTime.now());

        testAttendanceDTO = new AttendanceDTO();
        testAttendanceDTO.setId(1L);
        testAttendanceDTO.setEmployeeId(1L);
        testAttendanceDTO.setEmployeeName("John Doe");
        testAttendanceDTO.setAttendanceDate(testDate);
        testAttendanceDTO.setStatus("PRESENT");
        testAttendanceDTO.setCheckInTime(LocalDateTime.of(2023, 10, 15, 9, 0, 0));
        testAttendanceDTO.setCheckOutTime(LocalDateTime.of(2023, 10, 15, 17, 0, 0));
        testAttendanceDTO.setRemarks("On time");
    }

    @Test
    @DisplayName("Given valid attendance DTO when marking attendance then return saved attendance DTO")
    void givenValidAttendanceDTO_whenMarkAttendance_thenReturnSavedAttendanceDTO() {
        // Arrange
        AttendanceDTO inputDTO = new AttendanceDTO();
        inputDTO.setEmployeeId(1L);
        inputDTO.setAttendanceDate(testDate);
        inputDTO.setStatus("PRESENT");
        inputDTO.setCheckInTime(LocalDateTime.of(2023, 10, 15, 9, 0, 0));
        inputDTO.setCheckOutTime(LocalDateTime.of(2023, 10, 15, 17, 0, 0));
        inputDTO.setRemarks("On time");

        when(employeeRepository.findById(1L)).thenReturn(Optional