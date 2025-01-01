package com.forty.viro.common.dto.dsc;

import com.forty.viro.common.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class DscUserInfoRequest extends PageRequest implements Serializable {
    private Long userId; // 用户ID

    private String userName; // 用户名称

    private String cardId; // 身份证号码

    private String email; // 邮箱

    private String phoneNumber; // 手机号码

    private String sex; // 性别

    private String nation; // 国籍
}
