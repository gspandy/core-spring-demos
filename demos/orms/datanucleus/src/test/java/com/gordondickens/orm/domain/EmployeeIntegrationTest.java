package com.gordondickens.orm.domain;

import com.gordondickens.orm.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeIntegrationTest {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeIntegrationTest.class);

    @Autowired
    EmployeeService employeeService;

    @Test
    public void testMarkerMethod() {
        Employee employee = new Employee();
        employee.setFirstName("Cletus");
        employee.setLastName("Fetus");

        employeeService.saveEmployee(employee);
        assertNotNull("Employee MUST exist", employee);
        assertNotNull("Employee MUST have PK", employee.getId());
        logger.debug("Employee {} Saved", employee.getId());


        Employee employee1 = employeeService.findEmployee(employee.getId());
        assertSame("Employee Must be Found by ID", employee1.getId(), employee.getId());
    }
}

