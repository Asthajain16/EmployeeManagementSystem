package com.assignment.EmployeeManagementSystem.Controller;

import com.assignment.EmployeeManagementSystem.Model.Employee;
import com.assignment.EmployeeManagementSystem.Repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Test
    void testAddEmployee_Success() throws Exception {
        Employee employee = Employee.builder()
                .name("Astha Jain")
                .email("astha@example.com")
                .department("Engineering")
                .salary(50000.0)
                .joiningDate(LocalDate.now())
                .build();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Astha Jain"))
                .andExpect(jsonPath("$.email").value("astha@example.com"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        Employee emp1 = Employee.builder()
                .name("Sakshi")
                .email("sakshi@example.com")
                .department("HR")
                .salary(45000.0)
                .joiningDate(LocalDate.now())
                .build();

        Employee emp2 = Employee.builder()
                .name("Sam")
                .email("Sam@example.com")
                .department("IT")
                .salary(60000.0)
                .joiningDate(LocalDate.now())
                .build();

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void testGetEmployeeById_Found() throws Exception {
        Employee employee = Employee.builder()
                .name("Charu")
                .email("charu@example.com")
                .department("Finance")
                .salary(55000.0)
                .joiningDate(LocalDate.now())
                .build();

        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(get("/api/employees/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Charu"));
    }

    @Test
    void testGetEmployeeById_NotFound() throws Exception {
        mockMvc.perform(get("/api/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateEmployee_Success() throws Exception {
        Employee original = Employee.builder()
                .name("Daisy")
                .email("daisy@example.com")
                .department("Sales")
                .salary(52000.0)
                .joiningDate(LocalDate.now())
                .build();

        Employee saved = employeeRepository.save(original);

        Employee updated = Employee.builder()
                .id(saved.getId())
                .name("Daisy Updated")
                .email("daisy@example.com")
                .department("Marketing")
                .salary(55000.0)
                .joiningDate(LocalDate.now())
                .build();

        mockMvc.perform(put("/api/employees/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.department").value("Marketing"))
                .andExpect(jsonPath("$.name").value("Daisy Updated"));
    }

    @Test
    void testUpdateEmployee_NotFound() throws Exception {
        Employee updated = Employee.builder()
                .name("Ed")
                .email("ed@example.com")
                .department("Admin")
                .salary(47000.0)
                .joiningDate(LocalDate.now())
                .build();

        mockMvc.perform(put("/api/employees/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEmployee_Success() throws Exception {
        Employee employee = Employee.builder()
                .name("Farhan")
                .email("farhan@example.com")
                .department("Support")
                .salary(49000.0)
                .joiningDate(LocalDate.now())
                .build();

        Employee saved = employeeRepository.save(employee);

        mockMvc.perform(delete("/api/employees/{id}", saved.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEmployee_NotFound() throws Exception {
        mockMvc.perform(delete("/api/employees/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
