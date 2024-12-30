package com.forty.viro.provider.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("dsc_bank_card")
public class DscBankCard implements Serializable {

    @TableId
    private Long creditId; // 卡id

    @TableField("credit_account")
    private String creditAccount; // 银行卡账号

    @TableField("credit_belong")
    private String creditBelong; // 银行卡所属人

    @TableField("bank_name")
    private String bankName; // 开户行

    @TableField("open_time")
    private String openTime; // 开户时间

    @TableField("remain_amount")
    private BigDecimal remainAmount; // 银行卡余额

    @TableField("credit_type")
    private Integer creditType; // 银行卡种类，0: 借记卡，1：信用卡

    @TableField("credit_status")
    private Integer creditStatus; // 银行卡状态，0: 有效，1：挂失，2：过期

    @TableField("transaction_limit")
    private BigDecimal transactionLimit; // 交易限制

    @TableField("user_id")
    private Long userId; // 用户id

    @TableField("remark")
    private String remark; // 备注

    @TableLogic
    @TableField("is_delete")
    private Integer isDelete; // 是否删除，逻辑删除标识

    @TableField("create_time")
    private Date createTime; // 创建时间

    @TableField("update_time")
    private Date updateTime; // 更新时间

    @TableField(exist = false)
    private static final long serialVersionUID = 1L; // 序列化ID
}
