package com.forty.viro.common.model;


import com.forty.viro.common.enums.ErrorCode;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 14545123L;

    private int code = 200;

    private String msg = "successfully";

    private T data;

    public BaseResponse() {}
    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public BaseResponse(int code, String msg) {
        this(code, msg, null);
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode) { this(errorCode.getCode(), errorCode.getMessage(), null); }
}