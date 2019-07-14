package com.luoromeo.bms.domain.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * @description
 * @author zhanghua.luo
 * @date 2019年07月14日 11:47
 * @modified By
 */
public class Test {

    public static void main(String[] args) {
        ImportParams params = new ImportParams();
        params.setTitleRows(14);
        params.setHeadRows(1);
        long start = new Date().getTime();
        List<WxBillExcelEntity> list = ExcelImportUtil.importExcel(
                new File("C:\\Users\\zhang\\Desktop\\账单\\微信支付账单(20190401-20190630).xlsx"),
                WxBillExcelEntity.class, params);

        // 剔除交易状态为空的，取得所有账单数据
        List<WxBillExcelEntity> all = list.stream().filter(wxBillExcelEntity -> StringUtils.isNotBlank(wxBillExcelEntity.getTransactionStatus())).collect(Collectors.toList());
        // 过滤掉收支状态为/的，这些都是充值到零钱
        all = all.stream().filter(wxBillExcelEntity -> !"/".equals(wxBillExcelEntity.getType().trim())).collect(Collectors.toList());
        // 按照商户单号分组
        Map<String, List<WxBillExcelEntity>> orderNoMap = all.stream().collect(Collectors.groupingBy(WxBillExcelEntity::getOrderNo));
        // 同一个订单号出现两次的，并且有一次为退款的，则该笔订单不记录
        List<WxBillExcelEntity> result = new ArrayList<>();
        orderNoMap.forEach((k, v) -> {
            boolean flag = true;
            if (v.size() == 2) {
                for (WxBillExcelEntity wxBillExcelEntity : v) {
                    if (wxBillExcelEntity.getTransactionStatus().trim().equals("已全额退款")) {
//                        result.addAll(v);
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                result.addAll(v);
            }
        });



        // 按照收支状态分组
        Map<String, List<WxBillExcelEntity>> typeMap = result.stream().collect(Collectors.groupingBy(WxBillExcelEntity::getType));

        System.out.println(new Date().getTime() - start);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
    }
}
