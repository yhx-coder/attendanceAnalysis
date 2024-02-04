package com.lincao.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.lincao.dao.AttendanceRecordMapper;
import com.lincao.dao.EmployeeMapper;
import com.lincao.pojo.AttendanceRecord;
import com.lincao.pojo.Employee;
import com.lincao.util.CalendarUtil;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/4 15:54
 */
@Slf4j
public class MyDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final int BATCH_COUNT = 4;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    private String timePattern = "HH:mm";
    private final String dataPrefix;
    private AttendanceRecordManager attendanceRecordManager;
    private EmployeeManager employeeManager;

    private boolean needAddEmployee;

    public MyDataListener(String dataPrefix, AttendanceRecordManager attendanceRecordManager,
                          EmployeeManager employeeManager, boolean needAddEmployee) {
        this.dataPrefix = dataPrefix;
        this.attendanceRecordManager = attendanceRecordManager;
        this.employeeManager = employeeManager;
        this.needAddEmployee = needAddEmployee;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        cachedDataList.add(data);
        if (cachedDataList.size() == BATCH_COUNT) {
            saveData();
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("数据存储结束");
    }

    /**
     * 存储数据库
     * TODO: 后续考虑改成 batchInsert
     */
    private void saveData() {
        Map<Integer, String> nameMap = cachedDataList.get(1);
        int employeeId = Integer.parseInt(nameMap.get(0));
        String name = nameMap.get(1);
        Map<Integer, String> checkInMap = cachedDataList.get(0);
        Map<Integer, String> checkOutMap = cachedDataList.get(2);
        if (needAddEmployee) {
            Employee employee = new Employee();
            employee.setEmployeeId(employeeId);
            employee.setName(name);
            employeeManager.addEmployee(employee);
        }
        for (int i = 2; i < checkOutMap.size(); i++) {
            int day = i - 1;
            Date date = CalendarUtil.getDateFromString(dataPrefix, day);
            String first = checkInMap.get(i);
            String second = checkOutMap.get(i);
            if (first == null && second == null) {
                continue;
            }
            AttendanceRecord attendanceRecord = new AttendanceRecord();
            attendanceRecord.setEmployeeId(employeeId);
            attendanceRecord.setName(name);
            attendanceRecord.setWorkdayDate(date);
            if (first != null && second != null) {
                Time checkInTime = CalendarUtil.getTimeFromString(first, timePattern);
                attendanceRecord.setCheckInTime(checkInTime);
                Time checkOutTime = CalendarUtil.getTimeFromString(second, timePattern);
                attendanceRecord.setCheckOutTime(checkOutTime);
                attendanceRecord.setIsFullAttendance(true);
            }
            if (first != null && second == null) {
                Time checkInTime = CalendarUtil.getTimeFromString(first, timePattern);
                attendanceRecord.setCheckInTime(checkInTime);
                attendanceRecord.setIsFullAttendance(false);
            }
            if (first == null) {
                Time checkInTime = CalendarUtil.getTimeFromString(second, timePattern);
                attendanceRecord.setCheckInTime(checkInTime);
                attendanceRecord.setIsFullAttendance(false);
            }
            attendanceRecordManager.importAttendanceRecord(attendanceRecord);
        }
    }
}
