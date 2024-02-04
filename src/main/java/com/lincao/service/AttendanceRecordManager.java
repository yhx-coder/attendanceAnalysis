package com.lincao.service;

import com.lincao.pojo.AttendanceRecord;
import com.lincao.pojo.EmployeeAttendanceResponse;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/1 23:43
 */
public interface AttendanceRecordManager {
    boolean importAttendanceRecord(AttendanceRecord attendanceRecord);

    void getAttendanceRecordsFromRaw(String filepath, boolean needAddEmployee);

    /**
     * 获取按天聚合异常考勤人员
     */
    Map<Date, List<EmployeeAttendanceResponse>> getAbnormalEmployeesInMonthGroupByDay(int year, int month);

}
