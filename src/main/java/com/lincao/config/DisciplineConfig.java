package com.lincao.config;

import java.sql.Time;
import java.util.List;

/**
 * @author: ming
 * @date: 2024/2/3 20:27
 */
public class DisciplineConfig {
    // 最晚上班时间
    public static Time LATEST_START_TIME = Time.valueOf("08:29:59");
    // 最早下班时间
    public static Time EARLIEST_END_TIME = Time.valueOf("17:00:00");
}
