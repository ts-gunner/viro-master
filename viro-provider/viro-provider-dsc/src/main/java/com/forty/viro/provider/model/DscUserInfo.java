package com.forty.viro.provider.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("dsc_user_info")
public class DscUserInfo implements Serializable {

    @TableId("user_id")
    private Long userId; // 用户ID

    @TableField("user_name")
    private String userName; // 用户名称

    @TableField("card_id")
    private String cardId; // 身份证号码

    @TableField("email")
    private String email; // 邮箱

    @TableField("phone_number")
    private String phoneNumber; // 手机号码

    @TableField("address")
    private String address; // 地址

    @TableField("birth")
    private String birth; // 生日，格式：2024-01-01

    @TableField("sex")
    private String sex; // 性别

    @TableField("career")
    private String career; // 职业

    @TableField("nation")
    private String nation; // 国籍

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
