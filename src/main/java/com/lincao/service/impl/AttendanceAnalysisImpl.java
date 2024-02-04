package com.lincao.service.impl;

import com.lincao.pojo.EmployeeAttendanceResponse;
import com.lincao.service.AttendanceAnalysis;
import com.lincao.service.AttendanceRecordManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/2 11:56
 */

@Service
public class AttendanceAnalysisImpl implements AttendanceAnalysis {
    @Autowired
    private AttendanceRecordManager attendanceRecordManager;

    @Override
    public void attendanceRecordsPreparation(String filepath, boolean needAddEmployee) {
        attendanceRecordManager.getAttendanceRecordsFromRaw(filepath, needAddEmployee);
    }

    @Override
    public void attendanceRecordsPreparation(String filepath) {
        attendanceRecordsPreparation(filepath,false);
    }

    @Override
    public Map<Date, List<EmployeeAttendanceResponse>> getAbnormalAttendanceByDay(int year, int month) {

        return attendanceRecordManager.getAbnormalEmployeesInMonthGroupByDay(year, month);
    }
}
