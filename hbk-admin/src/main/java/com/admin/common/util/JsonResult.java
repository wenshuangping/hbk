package com.admin.common.util;

import com.admin.constant.JsonConstant;

import java.io.Serializable;

/**
 * @param <T>
 * @author wenhaitao
 */
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 8107951316406484761L;

    private int code;
    private String message;
    private int operStatus;


    private T data;

    public JsonResult() {
        this.code = JsonConstant.SUCCESS;
    }

    public JsonResult(String errorMessage) {
        this(JsonConstant.ERROR, errorMessage, 0, null);
    }

    public JsonResult(Exception e) {
        this(JsonConstant.ERROR, e.getMessage(), 0, null);
    }

    public JsonResult(T data) {
        this(JsonConstant.SUCCESS, "", 0, data);
    }

    public JsonResult(int code, String message) {
        this(code, message, 0, null);
    }

    public JsonResult(int code, String message, int operStatus, T data) {
        super();
        this.code = code;
        this.message = message;
        this.operStatus = operStatus;
        this.data = data;
    }


    public int getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(int operStatus) {
        this.operStatus = operStatus;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
