package com.cloud.business.castlers.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;

public class OfficeExportUtil {

    /**
     *
     */
    private static final Integer EXPORT_EXCEL_MAX_NUM = 20000;

    /**
     * 获取导出的Workbook对象
     *
     * @param sheetName 页签名
     * @param clazz     类对象
     * @param list      导出的数据集合
     * @return
     * @author xiagwei
     * @date 2020/2/10 4:45 PM
     */
    public static Workbook getWorkbook(String sheetName, Class clazz, List<?> list) {
        //判断数据是否为空
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        if (list.size() > EXPORT_EXCEL_MAX_NUM) {
            list = new ArrayList<>();
        }
//        log.info("***********"+sheetName+"的导出数据行数为"+list.size()+"");
        //获取导出参数
        ExportParams exportParams = new ExportParams();
        //设置导出样式
        exportParams.setStyle(ExcelDayExportStyler.class);
        //设置sheetName
        exportParams.setSheetName(sheetName);
        //输出workbook流
        return ExcelExportUtil.exportExcel(exportParams, clazz, list);
    }

}
