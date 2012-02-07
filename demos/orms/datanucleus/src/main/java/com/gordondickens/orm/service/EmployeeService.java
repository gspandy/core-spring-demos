package com.gordondickens.orm.service;

import com.gordondickens.orm.domain.Employee;

import java.util.List;

public interface EmployeeService {

    public abstract long countAllEmployees();


    public abstract void deleteEmployee(Employee employee);


    public abstract Employee findEmployee(Long id);


    public abstract List<Employee> findAllEmployees();


    public abstract List<Employee> findEmployeeEntries(int firstResult, int maxResults);


    public abstract void saveEmployee(Employee employee);


    public abstract Employee updateEmployee(Employee employee);

}
