package com.luoromeo.bms.domain.importer;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description 阿里订单excel实体
 * @author zhanghua.luo
 * @date 2019年07月13日 15:24
 * @modified By
 */
@Data
public class AliBillExcelEntity implements Serializable {

    private static final long serialVersionUID = 306441365695216439L;

    /**
     * 交易对手
     */
    @Excel(name = "交易对方")
    private String counterparty;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String item;

    /**
     * 交易金额
     */
    @Excel(name = "金额（元）")
    private BigDecimal money;

    /**
     * 类型 0支出1收入
     */
    @Excel(name = "收/支")
    private String type;

    /**
     * 交易时间
     */
    @Excel(name = "付款时间")
    private Date transactionTime;

    /**
     * 交易状态
     */
    @Excel(name = "交易状态")
    private String transactionStatus;

    /**
     * 交易号
     */
    @Excel(name = "交易号")
    private String transactionalNo;

    /**
     * 商家订单号
     */
    @Excel(name = "商家订单号")
    private String orderNo;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    public String getCounterparty() {
        if (counterparty == null) {
            return "";
        }
        return counterparty.trim();
    }

    public String getItem() {
        if (item == null) {
            return "";
        }
        return item.trim();
    }

    public String getType() {
        if (type == null) {
            return "";
        }
        return type.trim();
    }

    public String getTransactionStatus() {
        if (transactionStatus == null) {
            return "";
        }
        return transactionStatus.trim();
    }

    public String getTransactionalNo() {
        if (transactionalNo == null) {
            return "";
        }
        return transactionalNo.trim();
    }

    public String getOrderNo() {
        if (orderNo == null) {
            return "";
        }
        return orderNo.trim();
    }

    public String getRemark() {
        if (remark == null) {
            return "";
        }
        return remark.trim();
    }
}
