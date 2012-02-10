package com.gordondickens.orm.repository;

import com.gordondickens.orm.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository
        extends JpaSpecificationExecutor<Employee>, 
           JpaRepository<Employee, Long> {

}
