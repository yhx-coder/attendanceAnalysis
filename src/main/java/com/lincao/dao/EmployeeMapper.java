package com.lincao.dao;


import com.lincao.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/1 22:41
 */
@Repository
public interface EmployeeMapper {
    int addEmployee(Employee employee);

    Employee getEmployeeById(int employeeId);

    List<Employee> getAllEmployee();
}
