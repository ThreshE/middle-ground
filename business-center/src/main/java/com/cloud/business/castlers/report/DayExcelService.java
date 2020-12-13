package com.cloud.business.castlers.report;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.cloud.business.alibaba.ocean.rawsdk.common.SDKResult;
import com.cloud.business.alibaba.trade.param.*;
import com.cloud.business.castlers.manage.TradeManage;
import com.cloud.business.castlers.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @description:
 * @author: jes
 * @date: 2020/9/10 18:21
 */

@Slf4j
@RestController
@RequestMapping("tradeExcel")
@Api(value = "报表接口", tags = "报表接口")
public class DayExcelService {

    @Autowired
    private TradeManage tradeManage;

    private final String timeArea = " 00:00:00";

    @GetMapping("orderDayReportExcel")
    @ApiOperation(value = "导出支付订单，没有传入时间就默认是前一天")
    public void dayExcel(AlibabaTradeGetBuyerOrderListParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (param.getCreateStartTime() == null) {
            Date startDate = DateUtil.parse(DateUtil.format(DateUtil.addDays(new Date(), -1), DateUtil.FORMAT2) + timeArea, DateUtil.FORMAT1);
            param.setCreateStartTime(startDate);
        }
        if (param.getCreateEndTime() == null) {
            Date endDate = DateUtil.parse(DateUtil.format(new Date(), DateUtil.FORMAT2) + timeArea, DateUtil.FORMAT1);
            param.setCreateEndTime(endDate);
        }
        File sourceFile = ResourceUtils.getFile("classpath:excel/orderDayReportExcel.xls");
        if (sourceFile == null) {
            throw new Exception("没有模板文件");
        }
        String absolutePath = sourceFile.getAbsolutePath();

        TemplateExportParams params = new TemplateExportParams(
                absolutePath);

        param.setOrderStatus("waitsellersend");
        //因为没有查询支持成功的状态，所以先查询三次，确保是成功支付
        SDKResult<AlibabaTradeGetBuyerOrderListResult> buyerOrderList = tradeManage.getBuyerOrderList(param);
        AlibabaOpenplatformTradeModelTradeInfo[] result0 = buyerOrderList.getResult().getResult();

        param.setOrderStatus("waitbuyerreceive");
        AlibabaOpenplatformTradeModelTradeInfo[] result2 = tradeManage.getBuyerOrderList(param).getResult().getResult();

        param.setOrderStatus("success");
        AlibabaOpenplatformTradeModelTradeInfo[] result3 = tradeManage.getBuyerOrderList(param).getResult().getResult();

        List<AlibabaOpenplatformTradeModelTradeInfo> result1 = new ArrayList<>();
        if (result0.length > 0) {
            for (int i = 0; i < result0.length; i++) {
                result1.add(result0[i]);
            }
        }
        if (result2.length > 0) {
            for (int i = 0; i < result2.length; i++) {
                result1.add(result2[i]);
            }
        }
        if (result3.length > 0) {
            for (int i = 0; i < result3.length; i++) {
                result1.add(result3[i]);
            }
        }


        if (CollectionUtils.isEmpty(result1)) {
            return;
        }
        BigDecimal totalAmount = new BigDecimal(0);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < result1.size(); i++) {
            int a = i + 1;
            AlibabaOpenplatformTradeModelOrderBaseInfo baseInfo = result1.get(i).getBaseInfo();
            AlibabaOpenplatformTradeModelProductItemInfo[] productItems = result1.get(i).getProductItems();

            for (int t = 0; t < productItems.length; t++) {
                Map<String, String> lm = new HashMap<String, String>();
                lm.put("yesId", "YS00000" + a);

                lm.put("createTime", baseInfo.getCreateTime() != null ? DateUtil.format(baseInfo.getCreateTime(), DateUtil.FORMAT1) : "");
                lm.put("id", String.valueOf(baseInfo.getId()));
                lm.put("name", productItems[t].getName());
                lm.put("quantity", String.valueOf(productItems[t].getQuantity()));
                lm.put("price", String.valueOf(productItems[t].getPrice()));
                lm.put("shippingFee", String.valueOf(baseInfo.getShippingFee()));
                //一个订单的总金额
                lm.put("totalAmount", String.valueOf(baseInfo.getTotalAmount()));
                lm.put("buyername", String.valueOf(baseInfo.getBuyerUserId()));
                listMap.add(lm);
            }
            totalAmount = totalAmount.add(baseInfo.getTotalAmount());

        }
        map.put("maplist", listMap);
        map.put("totalAmount", String.valueOf(totalAmount));
        //先假定是0.86的汇率，到时候再接入api
        BigDecimal bigDecimal = new BigDecimal("0.8611");
        map.put("rate", String.valueOf(bigDecimal));

        double v = totalAmount.multiply(bigDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        map.put("totalAmountRate", v);


        map.put("RMB", "付款總金額（RMB）");
        map.put("thirdParty", "實時匯率（認可第三方）");
        map.put("HKD", "付款總金額（HKD）");

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        ServletOutputStream outputStream = null;
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\" + "33.xls");
        response.setContentType("application/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("orderDayReportExcel.xls","utf-8"));
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();

        } finally {
            os.close();
        }
        return;
    }


    @GetMapping("refundDayReportExcel")
    @ApiOperation(value = "导出退款订单，没有传入时间就默认是前一天")
    public void refundExcel(AlibabaTradeRefundBuyerQueryOrderRefundListParam param, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (param.getApplyStartTime() == null) {
            Date startDate = DateUtil.parse(DateUtil.format(DateUtil.addDays(new Date(), -1), DateUtil.FORMAT2) + timeArea, DateUtil.FORMAT1);
            param.setApplyStartTime(startDate);
        }
        if (param.getApplyEndTime() == null) {
            Date endDate = DateUtil.parse(DateUtil.format(new Date(), DateUtil.FORMAT2) + timeArea, DateUtil.FORMAT1);
            param.setApplyEndTime(endDate);
        }
        AlibabaTradeRefundOpQueryOrderRefundListResult result = tradeManage.queryOrderRefundList(param).getResult().getResult();
        AlibabaTradeRefundOpOrderRefundModel[] opOrderRefundModels = result.getOpOrderRefundModels();

        if (opOrderRefundModels.length < 1) {
            return;
        }

        File sourceFile = ResourceUtils.getFile("classpath:excel/refundDayReportExcel.xls");
        if (sourceFile == null) {
            throw new Exception("没有模板文件");
        }
        String absolutePath = sourceFile.getAbsolutePath();
        TemplateExportParams params = new TemplateExportParams(
                absolutePath);

        BigDecimal totalAmount = new BigDecimal(0);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < opOrderRefundModels.length; i++) {
            int a = i + 1;
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("yesId", "YesStyle000" + a);
            lm.put("applyCreat", DateUtil.format(opOrderRefundModels[i].getGmtApply(), DateUtil.FORMAT1));
            lm.put("orderId", String.valueOf(opOrderRefundModels[i].getOrderId()));


            Long applyCarriage = opOrderRefundModels[i].getApplyCarriage();
            Long applyPayment = opOrderRefundModels[i].getApplyPayment();
            Long total = applyCarriage + applyPayment;
            lm.put("totalPrice", String.valueOf(total));
            lm.put("buyername", String.valueOf(opOrderRefundModels[i].getBuyerUserId()));
            totalAmount = totalAmount.add(new BigDecimal(total));
            listMap.add(lm);
        }
        map.put("maplist", listMap);

        map.put("RMB", "退款總金額（RMB）");
        map.put("thirdParty", "實時匯率（認可第三方）");
        map.put("HKD", "退款總金額(HKD)");

        map.put("totalAmount", String.valueOf(totalAmount));
        BigDecimal bigDecimal = new BigDecimal("0.8611");
        map.put("rate", String.valueOf(bigDecimal));

        double v = totalAmount.multiply(bigDecimal).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        map.put("totalAmountRate", v);


        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        ServletOutputStream outputStream = null;
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\" + "55.xls");
        response.setContentType("application/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("refundDayReportExcel.xls","utf-8"));
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
            os.flush();

        } finally {
            os.close();
        }
    }


    public static void main(String[] args) throws IOException {


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String format = formatter.format(calendar.getTime());
        System.out.println("format = " + format);
//        DateUtil.parse();

        TemplateExportParams params = new TemplateExportParams(
                "excel/44.xls");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("date", "2014-12-25");
        map.put("money", 2000000.00);
        map.put("upperMoney", "付款總金額（RMB）");
        map.put("company", "执笔潜行科技有限公司");
        map.put("bureau", "财政局");
        map.put("person", "JueYue");
        map.put("phone", "1879740****");
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> lm = new HashMap<String, String>();
            lm.put("id", i + 1 + "");
            lm.put("zijin", i * 10000 + "");
            lm.put("bianma", "A001");
            lm.put("mingcheng", "设计");
            lm.put("xiangmumingcheng", "EasyPoi " + i + "期");
            lm.put("quancheng", "开源项目");
            lm.put("sqje", i * 10000 + "");
            lm.put("hdje", i * 10000 + "");

            listMap.add(lm);
        }
        map.put("maplist", listMap);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);

        long time = new Date().getTime();
        FileOutputStream fos = new FileOutputStream("C:\\Users\\admin\\Desktop\\" + "33.xls");
        workbook.write(fos);
        fos.close();
    }

}
