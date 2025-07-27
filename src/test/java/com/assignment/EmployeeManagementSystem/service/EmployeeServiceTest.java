package com.assignment.EmployeeManagementSystem.service;


import com.assignment.EmployeeManagementSystem.Model.Employee;
import com.assignment.EmployeeManagementSystem.Repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Amit");
        employee.setEmail("amit@example.com");
        employee.setDepartment("IT");
        employee.setSalary(50000.0);
        employee.setJoiningDate(LocalDate.of(2024, 1, 10));
    }

    @Test
    void addEmployee_WhenEmailIsUnique_ShouldSaveEmployee() {
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(false);
        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee saved = employeeService.addEmployee(employee);

        assertEquals(employee.getEmail(), saved.getEmail());
        verify(employeeRepository).save(employee);
    }

    @Test
    void addEmployee_WhenEmailExists_ShouldThrowException() {
        when(employeeRepository.existsByEmail(employee.getEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> employeeService.addEmployee(employee));
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void getAllEmployees_ShouldReturnList() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(1, employees.size());
        verify(employeeRepository).findAll();
    }

    @Test
    void getEmployeeById_WhenExists_ShouldReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> found = employeeService.getEmployeeById(1L);

        assertTrue(found.isPresent());
        assertEquals("Amit", found.get().getName());
    }

    @Test
    void updateEmployee_WhenExists_ShouldUpdateAndReturn() {
        Employee updated = new Employee();
        updated.setName("Updated");
        updated.setEmail("updated@example.com");
        updated.setDepartment("HR");
        updated.setSalary(60000.0);
        updated.setJoiningDate(LocalDate.of(2024, 2, 1));

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(updated);

        Optional<Employee> result = employeeService.updateEmployee(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().getName());
    }

    @Test
    void deleteEmployee_WhenExists_ShouldDeleteAndReturnTrue() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        boolean deleted = employeeService.deleteEmployee(1L);

        assertTrue(deleted);
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    void deleteEmployee_WhenNotExists_ShouldReturnFalse() {
        when(employeeRepository.existsById(1L)).thenReturn(false);

        boolean deleted = employeeService.deleteEmployee(1L);

        assertFalse(deleted);
        verify(employeeRepository, never()).deleteById(1L);
    }
}
