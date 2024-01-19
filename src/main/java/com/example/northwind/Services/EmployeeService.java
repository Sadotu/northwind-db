package com.example.northwind.Services;

import com.example.northwind.DTO.Input.EmployeeInputDTO;
import com.example.northwind.DTO.Output.EmployeeOutputDTO;
import com.example.northwind.DTO.Output.SimpleEmployeeOutputDTO;
import com.example.northwind.DTO.Output.TerritoryOutputDTO;
import com.example.northwind.Exceptions.EmployeeAlreadyExistsException;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Employee;
import com.example.northwind.Models.Territory;
import com.example.northwind.Repositories.EmployeeRepository;
import com.example.northwind.Repositories.TerritoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TerritoryService territoryService;
    private final TerritoryRepository territoryRepository;

    public EmployeeService(EmployeeRepository employeeRepository, TerritoryService territoryService, TerritoryRepository territoryRepository) {
        this.employeeRepository = employeeRepository;
        this.territoryService = territoryService;
        this.territoryRepository = territoryRepository;
    }

    public EmployeeOutputDTO transferModelToOutputDTO(Employee employee) {
        EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO();
        BeanUtils.copyProperties(employee, employeeOutputDTO);

        // Handle the 'reportsTo' relationship
        if (employee.getReportsTo() != null) {
            SimpleEmployeeOutputDTO reportsToDTO = new SimpleEmployeeOutputDTO();
            BeanUtils.copyProperties(employee.getReportsTo(), reportsToDTO);
            employeeOutputDTO.setReportsTo(reportsToDTO);
        }

        // Handle the 'territories' relationship
        if (employee.getTerritories() != null && !employee.getTerritories().isEmpty()) {
            List<TerritoryOutputDTO> territoryDTOs = employee.getTerritories().stream()
                    .map(territoryService::transferModelToOutputDTO)
                    .collect(Collectors.toList());
            employeeOutputDTO.setTerritories(territoryDTOs);
        }

        return employeeOutputDTO;
    }

    public Employee transferInputDTOToModel(EmployeeInputDTO employeeInputDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeInputDTO, employee);
        return employee;
    }

    public EmployeeOutputDTO createEmployee(EmployeeInputDTO employeeInputDTO) throws EmployeeAlreadyExistsException {
        if (employeeRepository.findById(employeeInputDTO.getEmployeeId()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee with name " +
                    employeeInputDTO.getFirstName() +
                    " " +
                    employeeInputDTO.getLastName() +
                    " and ID: " + employeeInputDTO.getEmployeeId() +
                    " already exists");
        }

        return transferModelToOutputDTO(employeeRepository.save(transferInputDTOToModel(employeeInputDTO)));
    }

    public EmployeeOutputDTO getEmployeeById(Short id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));;

        return transferModelToOutputDTO(employee);
    }

    public EmployeeOutputDTO updateEmployeeById(Short id, EmployeeOutputDTO employeeOutputDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));

        BeanUtils.copyProperties(employeeOutputDTO, existingEmployee, "id");

        return transferModelToOutputDTO(employeeRepository.save(existingEmployee));
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
}
