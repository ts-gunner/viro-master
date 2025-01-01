package com.forty.viro.common.model;


import com.forty.viro.common.enums.AiRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AiMessage {

    private String role;

    private String content;

    public AiMessage(AiRoleEnum role, String content) {
        this.role = role.getRoleName();
        this.content = content;
    }

}
