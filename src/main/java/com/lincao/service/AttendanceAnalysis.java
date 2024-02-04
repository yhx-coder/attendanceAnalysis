package com.lincao.service;

import com.lincao.pojo.EmployeeAttendanceResponse;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/2 12:58
 */
public interface AttendanceAnalysis {
    void attendanceRecordsPreparation(String filepath, boolean needAddEmployee);

    void attendanceRecordsPreparation(String filepath);

    Map<Date, List<EmployeeAttendanceResponse>> getAbnormalAttendanceByDay(int year, int month);
}
