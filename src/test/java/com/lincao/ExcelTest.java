package com.lincao;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.lincao.service.MyDataListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: ming
 * @date: 2024/2/4 13:39
 */
@Slf4j
public class ExcelTest {
    @Test
    public void TestEasyExcel(){
        String filepath = "D:\\IDEA\\project\\202401.xls";
        File file = new File(filepath);
        String datePrefix = file.getName().split("\\.")[0];
        EasyExcel.read(file, new AnalysisEventListener<Map<Integer, String>>() {
            private static final int BATCH_COUNT = 4;
            private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(Map<Integer, String> data, AnalysisContext context) {
                log.info("解析到一条数据:{}",data);
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
                log.info("所有数据解析完成！");
            }

            /**
             * 加上存储数据库
             */
            private void saveData() {
                log.info("{}条数据，开始存储数据库！", cachedDataList.size());
                log.info("存储数据库成功！");
            }
        }).ignoreEmptyRow(false).sheet().doRead();
    }
}
