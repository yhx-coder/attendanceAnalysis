package com.lincao;

import com.nlf.calendar.Holiday;
import com.nlf.calendar.util.HolidayUtil;
import org.junit.Test;

/**
 * @author: ming
 * @date: 2024/2/2 14:30
 */
public class LunarTest {

    @Test
    public void testGetHoliday(){
        String filepath = "";
        Holiday holiday = HolidayUtil.getHoliday("2021-04-2");
        System.out.println(holiday.isWork());
    }
}
