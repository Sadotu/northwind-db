package com.example.northwind.Services;

import com.example.northwind.DTO.Input.EmployeeInputDTO;
import com.example.northwind.DTO.Output.EmployeeOutputDTO;
import com.example.northwind.Exceptions.EmployeeAlreadyExistsException;
import com.example.northwind.Exceptions.ResourceNotFoundException;
import com.example.northwind.Models.Employee;
import com.example.northwind.Repositories.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeOutputDTO transferModelToOutputDTO(Employee employee) {
        EmployeeOutputDTO employeeOutputDTO = new EmployeeOutputDTO();
        BeanUtils.copyProperties(employee, employeeOutputDTO);

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

    public EmployeeOutputDTO getEmployeeById(short id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));;

        return transferModelToOutputDTO(employee);
    }
}
