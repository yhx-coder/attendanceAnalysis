package com.lincao.service.impl;

import com.lincao.dao.EmployeeMapper;
import com.lincao.pojo.Employee;
import com.lincao.service.EmployeeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: ming
 * @date: 2024/2/4 18:08
 */
@Service
public class EmployeeManagerImpl implements EmployeeManager {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int addEmployee(Employee employee) {
        return employeeMapper.addEmployee(employee);
    }
}
