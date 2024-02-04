package com.lincao.pojo;

import com.lincao.config.AttendanceStatusCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: ming
 * @date: 2024/2/2 12:14
 */
@Getter
@Setter
public class EmployeeAttendanceResponse {
    private String name;
    private Integer employeeId;
    private Date workday;
    private List<AttendanceStatusCode> attendanceStatusCodeList = new ArrayList<>();

    @Override
    public String toString() {
        return "EmployeeAttendance {" +
                "姓名='" + name + '\'' +
                ", 工号=" + employeeId +
                ", 异常情况列表=" + attendanceStatusCodeList +
                '}';
    }
}
