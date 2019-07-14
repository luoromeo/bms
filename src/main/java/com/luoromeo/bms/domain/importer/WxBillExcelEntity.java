package com.luoromeo.bms.domain.importer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description 微信订单excel实体
 * @author zhanghua.luo
 * @date 2019年07月13日 15:24
 * @modified By
 */
@Data
public class WxBillExcelEntity implements Serializable {
    private static final long serialVersionUID = 4082584167885187510L;

    /**
     * 交易对手
     */
    @Excel(name = "交易对方")
    private String counterparty;

    /**
     * 商品名称
     */
    @Excel(name = "商品")
    private String item;

    /**
     * 交易金额
     */
    @Excel(name ="金额(元)")
    private BigDecimal money;

    /**
     * 类型 0支出1收入
     */
    @Excel(name = "收/支")
    private String type;

    /**
     * 交易时间
     */
    @Excel(name = "交易时间")
    private String transactionTime;

    /**
     * 交易状态
     */
    @Excel(name = "当前状态")
    private String transactionStatus;

    /**
     * 交易号
     */
    @Excel(name = "交易单号")
    private String transactionalNo;

    /**
     * 商户单号
     */
    @Excel(name = "商户单号")
    private String orderNo;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
