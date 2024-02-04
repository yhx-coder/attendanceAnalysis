package com.lincao.service.impl;

import com.alibaba.excel.EasyExcel;
import com.lincao.config.AttendanceStatusCode;
import com.lincao.config.DisciplineConfig;
import com.lincao.dao.AttendanceRecordMapper;
import com.lincao.dao.EmployeeMapper;
import com.lincao.pojo.AttendanceRecord;
import com.lincao.pojo.Employee;
import com.lincao.pojo.EmployeeAttendanceResponse;
import com.lincao.service.AttendanceRecordManager;
import com.lincao.service.EmployeeManager;
import com.lincao.service.MyDataListener;
import com.lincao.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author: ming
 * @date: 2024/2/1 23:45
 */
@Service
public class AttendanceRecordManagerImpl implements AttendanceRecordManager {
    @Autowired
    private AttendanceRecordMapper attendanceRecordMapper;

    @Autowired
    private EmployeeManager employeeManager;
    @Autowired
    private EmployeeMapper employeeMapper;

    public boolean importAttendanceRecord(AttendanceRecord attendanceRecord) {
        int res = attendanceRecordMapper.addAttendanceRecord(attendanceRecord);
        return res != 0;
    }

    @Override
    public void getAttendanceRecordsFromRaw(String filepath, boolean needAddEmployee) {
        File file = new File(filepath);
        String datePrefix = file.getName().split("\\.")[0];
        EasyExcel.read(file, new MyDataListener(datePrefix, this, employeeManager, needAddEmployee))
                .ignoreEmptyRow(false).sheet().doRead();
    }

    @Override
    public Map<Date, List<EmployeeAttendanceResponse>> getAbnormalEmployeesInMonthGroupByDay(int year, int month) {
        Map<Date, List<EmployeeAttendanceResponse>> res = new HashMap<>();
        List<Employee> allEmployee = employeeMapper.getAllEmployee();
        List<Date> workdaysInMonth = CalendarUtil.getWorkdaysInMonth(year, month);
        for (Date date : workdaysInMonth) {
            for (Employee employee : allEmployee) {
                AttendanceRecord attendanceRecord = attendanceRecordMapper.getAttendanceStatusOnDateForEmployee(date, employee.getEmployeeId());
                if (isNormalAttendanceRecord(attendanceRecord)) {
                    continue;
                }
                EmployeeAttendanceResponse status = new EmployeeAttendanceResponse();
                status.setEmployeeId(employee.getEmployeeId());
                status.setName(employee.getName());
                status.setWorkday(date);
                if (attendanceRecord == null) {
                    status.getAttendanceStatusCodeList().add(AttendanceStatusCode.ABSENT);
                } else {
                    if (!attendanceRecord.getIsFullAttendance()) {
                        status.getAttendanceStatusCodeList().add(AttendanceStatusCode.MISSING);
                    }
                    if (isLate(attendanceRecord)) {
                        status.getAttendanceStatusCodeList().add(AttendanceStatusCode.LATE);
                    }
                    if (attendanceRecord.getIsFullAttendance() && isLeaveEarly(attendanceRecord)) {
                        status.getAttendanceStatusCodeList().add(AttendanceStatusCode.LEAVE_EARLY);
                    }
                }
                List<EmployeeAttendanceResponse> statusList = res.getOrDefault(date, new ArrayList<>());
                statusList.add(status);
                res.put(date, statusList);
            }
        }
        return res;
    }

    public boolean isNormalAttendanceRecord(AttendanceRecord attendanceRecord) {
        return attendanceRecord != null && attendanceRecord.getIsFullAttendance() && !isLate(attendanceRecord) && !isLeaveEarly(attendanceRecord);
    }

    public boolean isLate(AttendanceRecord attendanceRecord) {
        return DisciplineConfig.LATEST_START_TIME.before(attendanceRecord.getCheckInTime());
    }

    public boolean isLeaveEarly(AttendanceRecord attendanceRecord) {
        return DisciplineConfig.EARLIEST_END_TIME.after(attendanceRecord.getCheckOutTime());
    }

}
