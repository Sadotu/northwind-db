package com.example.northwind.Repositories;

import com.example.northwind.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Short> {
    Optional<Employee> findById(Short Id);
}
