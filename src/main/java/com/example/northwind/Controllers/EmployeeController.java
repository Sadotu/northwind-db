package com.example.northwind.Controllers;

import com.example.northwind.DTO.Input.EmployeeInputDTO;
import com.example.northwind.DTO.Output.EmployeeOutputDTO;
import com.example.northwind.Exceptions.EmployeeAlreadyExistsException;
import com.example.northwind.Services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeOutputDTO> createEmployee(@Valid @RequestBody EmployeeInputDTO employeeInputDTO) throws EmployeeAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeInputDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeOutputDTO> getEmployeeById(@PathVariable short id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
}
