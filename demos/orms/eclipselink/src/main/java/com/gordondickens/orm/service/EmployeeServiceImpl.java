package com.gordondickens.orm.service;

import com.gordondickens.orm.domain.Employee;
import com.gordondickens.orm.repository.EmployeeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
    EmployeeRepository employeeRepository;

	public long countAllEmployees() {
        return employeeRepository.count();
    }

	public void deleteEmployee(Employee employee) {
        employeeRepository.delete(employee);
    }

	public Employee findEmployee(Long id) {
        return employeeRepository.findOne(id);
    }

	public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

	public List<Employee> findEmployeeEntries(int firstResult, int maxResults) {
        return employeeRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

	public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
