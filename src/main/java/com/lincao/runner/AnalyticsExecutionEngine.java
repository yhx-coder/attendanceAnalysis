package com.lincao.runner;

import com.lincao.pojo.EmployeeAttendanceResponse;
import com.lincao.service.AttendanceAnalysis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: ming
 * @date: 2024/2/2 17:17
 */
@Component
@Slf4j
public class AnalyticsExecutionEngine implements ApplicationRunner {
    @Autowired
    private AttendanceAnalysis attendanceAnalysis;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, String> commandLine = CommandParseHelper.parseCommandLine(args);
        String needLoadAttendance = commandLine.get("needLoadAttendance");
        if ("true".equals(needLoadAttendance)) {
            String filepath = commandLine.get("filepath");
            String needLoadEmployeeParam = commandLine.get("needLoadEmployeeParam");
            boolean needAddEmployee = "true".equals(needLoadEmployeeParam);
            attendanceAnalysis.attendanceRecordsPreparation(filepath, needAddEmployee);
        }
        processGroupByDate();
    }

    private void processGroupByDate() {
        Map<Date, List<EmployeeAttendanceResponse>> abnormalAttendanceByDay =
                attendanceAnalysis.getAbnormalAttendanceByDay(2024, 1);
        if (!abnormalAttendanceByDay.isEmpty()) {
            // 将HashMap的条目转换为列表
            List<Date> keyList = new ArrayList<>(abnormalAttendanceByDay.keySet());
            Collections.sort(keyList);
            System.out.println("============存在考勤异常人员=============");
            for (Date key:keyList) {
                List<EmployeeAttendanceResponse> value = abnormalAttendanceByDay.get(key);
                System.out.print(key + ":");
                System.out.print(value);
                System.out.println();
                System.out.println("----------------------------------");
            }
        }

        System.out.println("========================");
    }


}
