package com.lincao.config;

/**
 * @author: ming
 * @date: 2024/2/2 12:21
 */
public enum AttendanceStatusCode {
    NORMAL("正常"),
    MISSING("缺卡"),
    ABSENT("旷工"),
    LATE("迟到"),
    LEAVE_EARLY("早退");

    private final String statusText;

    AttendanceStatusCode(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }

    @Override
    public String toString() {
        return statusText;
    }
}
