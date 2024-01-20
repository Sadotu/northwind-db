package com.example.northwind.Services;

import com.example.northwind.DTO.Output.EmployeeOutputDTO;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Employee;
import com.example.northwind.Models.Territory;
import com.example.northwind.Repositories.EmployeeRepository;
import com.example.northwind.Repositories.TerritoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService {

    private final EmployeeRepository employeeRepository;
    private final TerritoryRepository territoryRepository;

    public RelationshipService(EmployeeRepository employeeRepository, TerritoryRepository territoryRepository) {
        this.employeeRepository = employeeRepository;
        this.territoryRepository = territoryRepository;
    }

    public static EmployeeOutputDTO transferModelToOutputDTO(Employee employee) {
        EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO();
        BeanUtils.copyProperties(employee, employeeOutputDTO);

        return employeeOutputDTO;
    }

    @Transactional
    public EmployeeOutputDTO assignTerritoryToEmployee(String territoryId, Short employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        Territory territory = territoryRepository.findById(territoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Territory not found with id " + territoryId));

        if (employee.getTerritories().contains(territory)) {
            throw new IllegalStateException("The territory is already assigned to this employee.");
        }

        employee.getTerritories().add(territory);
        employee = employeeRepository.save(employee);

        return transferModelToOutputDTO(employee);
    }

    @Transactional
    public EmployeeOutputDTO unassignTerritoryFromEmployee(String territoryId, Short employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        Territory territory = territoryRepository.findById(territoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Territory not found with id " + territoryId));

        if (!employee.getTerritories().contains(territory)) {
            throw new IllegalStateException("The territory is not assigned to this employee.");
        }

        employee.getTerritories().remove(territory);
        employee = employeeRepository.save(employee);

        return transferModelToOutputDTO(employee);
    }
}
