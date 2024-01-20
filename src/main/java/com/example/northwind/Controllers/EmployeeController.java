package com.example.northwind.Controllers;

import com.example.northwind.DTO.Input.EmployeeInputDTO;
import com.example.northwind.DTO.Output.EmployeeOutputDTO;
import com.example.northwind.Exceptions.EmployeeAlreadyExistsException;
import com.example.northwind.Services.EmployeeService;
import com.example.northwind.Services.RelationshipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final RelationshipService relationshipService;

    public EmployeeController(EmployeeService employeeService, RelationshipService relationshipService) {
        this.employeeService = employeeService;
        this.relationshipService = relationshipService;
    }

    @PostMapping("/create")
    public ResponseEntity<EmployeeOutputDTO> createEmployee(@Valid @RequestBody EmployeeInputDTO employeeInputDTO) throws EmployeeAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeInputDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeOutputDTO> getEmployeeById(@PathVariable Short id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeOutputDTO> updateEmployeeById(@PathVariable Short id, @RequestBody EmployeeOutputDTO employeeOutputDTO) {
        return ResponseEntity.ok(employeeService.updateEmployeeById(id, employeeOutputDTO));
    }

    @PutMapping("/assign/{territoryId}/to/{employeeId}")
    public ResponseEntity<EmployeeOutputDTO> assignTerritoryToEmployee(@PathVariable String territoryId, @PathVariable Short employeeId) {
        return ResponseEntity.ok(relationshipService.assignTerritoryToEmployee(territoryId, employeeId));
    }
}
