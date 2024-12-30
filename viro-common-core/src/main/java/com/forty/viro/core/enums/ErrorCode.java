package com.forty.viro.core.enums;

public enum ErrorCode {
    FAIL(0, "Http Request Failed"),
    PARAM_ERROR(400, "Param Error"),
    AUTH_FAIL(401, "User Not Authenticated"),
    USER_NOT_LOGIN(40101, "User Not Logged"),
    SIGN_AUTH_FAILED(40102, "Sign Auth Failed"),
    AUTH_ROLE_FAILED(40103, "Auth Role Failed"),
    TOKEN_UNAVAILABLE(40104, "Token Unavailable"),

    DATA_NOT_EXIST(40401, "Data Not Exist"),
    SYSTEM_ERROR(500, "System Error");

    private final int code;

    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
