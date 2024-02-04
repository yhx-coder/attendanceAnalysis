package com.lincao;

import com.lincao.config.DisciplineConfig;
import org.junit.Test;

import java.sql.Time;

/**
 * @author: ming
 * @date: 2024/2/4 12:45
 */
public class TimeTest {
    @Test
    public void testTimeCompare(){
        Time time1 = Time.valueOf("17:05:30");
        Time time2 = DisciplineConfig.LATEST_START_TIME;

        if (time2.after(time1)) {
            System.out.println("time1 is earlier than time2");
        } else if (time2.before(time1)) {
            System.out.println("time1 is later than time2");
        } else {
            System.out.println("time1 is equal to time2");
        }
    }

}
