package com.luoromeo.bms;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import com.luoromeo.bms.domain.bill.Bill;
import com.luoromeo.bms.domain.bill.BillService;
import com.luoromeo.bms.domain.importer.AliBillExcelEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BmsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private BillService billService;

	@Test
	public void aliBillImport() {
		ImportParams params = new ImportParams();
		params.setTitleRows(4);
		params.setHeadRows(1);
		long start = new Date().getTime();
		List<AliBillExcelEntity> list = ExcelImportUtil.importExcel(
				new File("C:\\Users\\zhang\\Desktop\\账单\\alipay_record_20190713_1349_1.xlsx"),
				AliBillExcelEntity.class, params);
		// 剔除交易状态为空的，取得所有账单数据
		List<AliBillExcelEntity> all = list.stream().filter(aliBillExcelEntity -> StringUtils.isNotBlank(aliBillExcelEntity.getTransactionStatus())).collect(Collectors.toList());
		// 按照交易状态分组
		Map<String, List<AliBillExcelEntity>> m = all.stream().collect(Collectors.groupingBy(AliBillExcelEntity::getTransactionStatus));
		// 剔除收支状态为空的，取得所有收入/支出
		List<AliBillExcelEntity> typeNotNullList = all.stream().filter(aliBillExcelEntity -> StringUtils.isNotBlank(aliBillExcelEntity.getType())).collect(Collectors.toList());
		// 有的收入/支出没有订单号，没有订单号的根据交易号插入订单号
		typeNotNullList.forEach(aliBillExcelEntity -> {
			if (StringUtils.isBlank(aliBillExcelEntity.getOrderNo())) {
				aliBillExcelEntity.setOrderNo(aliBillExcelEntity.getTransactionalNo());
			}
		});
		// 按照订单号分组
		Map<String, List<AliBillExcelEntity>> orderNoMap = typeNotNullList.stream().collect(Collectors.groupingBy(AliBillExcelEntity::getOrderNo));
		// 同一个订单号出现两次的，并且有一次为退款的，则该笔订单不记录
		List<AliBillExcelEntity> result = new ArrayList<>();
		orderNoMap.forEach((k, v) -> {
			boolean flag = true;
			if (v.size() == 2) {
				for (AliBillExcelEntity aliBillExcelEntity : v) {
					if (aliBillExcelEntity.getTransactionStatus().trim().equals("退款成功")) {
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
		Map<String, List<AliBillExcelEntity>> typeMap = result.stream().collect(Collectors.groupingBy(AliBillExcelEntity::getType));
		// todo 保存至数据库
		// 统计收入/支出
		typeMap.forEach((k, v) -> {
			v.forEach(aliBillExcelEntity -> {
				Bill bill = new Bill();
				BeanUtils.copyProperties(aliBillExcelEntity, bill);
				if ("支出".equals(aliBillExcelEntity.getType().trim())) {
					bill.setType(1);
				} else {
					bill.setType(2);
				}
				bill.setSource(1);
//				SimpleFormatter formatter
				billService.save(bill);
			});


//			final BigDecimal[] sum = {BigDecimal.ZERO};
//			// 最大
//			final AliBillExcelEntity[] max = {new AliBillExcelEntity()};
//			// 最小
//			final BigDecimal[] min = {null};
//			v.forEach(aliBillExcelEntity -> {
//				sum[0] = sum[0].add(aliBillExcelEntity.getMoney());
//				if (max[0].getMoney() == null || aliBillExcelEntity.getMoney().compareTo(max[0].getMoney()) > 0) {
//					max[0] = aliBillExcelEntity;
//				}
//			});
//			System.out.println(k + ": " + sum[0] + "max: " + max[0]);
		});




		System.out.println(new Date().getTime() - start);
		System.out.println(list.size());
		System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
	}

	@Test
	public void wxBillImport() {

	}

}
