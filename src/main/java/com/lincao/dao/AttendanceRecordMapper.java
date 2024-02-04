package com.lincao.dao;

import com.lincao.pojo.AttendanceRecord;
import com.lincao.pojo.EmployeeAttendanceResponse;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/1 22:25
 */
@Repository
public interface AttendanceRecordMapper {
    AttendanceRecord getAttendanceStatusOnDateForEmployee(Date date, int employeeId);

    int addAttendanceRecord(AttendanceRecord attendanceRecord);

    int updateCheckOutTime(AttendanceRecord attendanceRecord);

}
