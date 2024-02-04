package com.lincao.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.sql.Date;
import java.sql.Time;

/**
 * @author: ming
 * @date: 2024/2/1 22:19
 */
@Getter
@Setter
@ToString
public class AttendanceRecord {
    private Long id;
    private String name;
    private Integer employeeId;
    private Date workdayDate;
    private Time checkInTime;
    private Time checkOutTime;
    private Boolean isFullAttendance;
    private String ext;
}
