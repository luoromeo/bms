package com.luoromeo.bms.domain.bill;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.luoromeo.bms.common.BaseEntity;

/**
 * @description 账单实体
 * @author zhanghua.luo
 * @date 2019年07月13日 15:11
 * @modified By
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Bill extends BaseEntity<Bill> {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 交易号 用于表示唯一性
     */
    private String transactionalNo;

    /**
     * 交易对手
     */
    private String counterparty;

    /**
     * 商品名称
     */
    private String item;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 类型 0未知1支出2收入
     */
    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transactionTime;

    /**
     * 来源 1支付宝2微信
     */
    private Integer source;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
}
