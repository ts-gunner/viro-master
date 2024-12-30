package com.forty.viro.provider.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DscUserInfoVo implements Serializable {
    private Long userId; // 用户ID

    private String userName; // 用户名称

    private String cardId; // 身份证号码

    private String email; // 邮箱

    private String phoneNumber; // 手机号码

    private String address; // 地址

    private String birth; // 生日，格式：2024-01-01

    private String sex; // 性别

    private String career; // 职业

    private String nation; // 国籍

    private Integer isDelete; // 是否删除，逻辑删除标识

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    private static final long serialVersionUID = 1L; // 序列化ID
}
