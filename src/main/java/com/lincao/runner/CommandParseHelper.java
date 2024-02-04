package com.lincao.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/4 20:58
 */
@Slf4j
public class CommandParseHelper {
    public static Map<String, String> parseCommandLine(ApplicationArguments args) {
        Map<String, String> params = new HashMap<>();
        List<String> filepathList = args.getOptionValues("file");
        if (filepathList == null || filepathList.size() != 1) {
            throw new RuntimeException("未指定读取的文件或文件数量多于一");
        }
        String filepath = filepathList.get(0);
        params.put("filepath", filepath);

        List<String> needLoadAttendanceParam = args.getOptionValues("needLoadAttendance");
        if (needLoadAttendanceParam == null || needLoadAttendanceParam.isEmpty()) {
            params.put("needLoadAttendance", "false");
        } else {
            params.put("needLoadAttendance", needLoadAttendanceParam.get(0).toLowerCase());
        }

        List<String> needLoadEmployeeParam = args.getOptionValues("needLoadEmployee");
        if (needLoadEmployeeParam == null || needLoadEmployeeParam.isEmpty()) {
            params.put("needLoadEmployeeParam", "false");
        } else {
            params.put("needLoadEmployeeParam", needLoadEmployeeParam.get(0).toLowerCase());
        }
        return params;
    }
}
