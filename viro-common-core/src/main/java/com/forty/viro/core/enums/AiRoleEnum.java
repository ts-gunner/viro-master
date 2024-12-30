package com.forty.viro.core.enums;

public enum AiRoleEnum {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant"),;

    private final String roleName;

    private AiRoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
