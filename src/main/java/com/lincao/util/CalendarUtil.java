package com.lincao.util;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;


import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author: ming
 * @date: 2024/2/2 15:04
 */
public class CalendarUtil {
    public static String getFormatDateForLunar(Date date) {
        String pattern = "YYYY-MM-DD";
        return DateFormatUtils.format(date, pattern);
    }

    public static boolean isWorkDay(Date date) {
        Holiday holiday = HolidayUtil.getHoliday(getFormatDateForLunar(date));
        if (holiday != null) {
            return holiday.isWork();
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        // 获取星期几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 判断是否是周六或周日
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    public static List<Date> getWorkdaysInMonth(int year, int month) {
        List<Date> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= days; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            Date date = new Date(calendar.getTime().getTime());
            if (isWorkDay(date)) {
                dateList.add(date);
            }
        }
        return dateList;
    }

    public static Date getDateFromString(String dataPrefix, int day) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date;
        if (day < 10) {
            date = dataPrefix + "0" + day;
        } else {
            date = dataPrefix + day;
        }
        return Date.valueOf(LocalDate.parse(date, formatter));
    }

    public static Time getTimeFromString(String timeString, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalTime localTime = LocalTime.parse(timeString, formatter);
        return Time.valueOf(localTime);
    }
}
