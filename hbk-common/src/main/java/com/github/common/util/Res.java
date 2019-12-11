package com.github.common.util;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应信息主体
 * @param <T>
 * @author wsp
 */
@Data
public class Res<T> implements Serializable {

    private static final long serialVersionUID = -5435775931050480209L;

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 0;

    public static final int FAIL = 1;

    public static final int NO_PERMISSION = 2;

    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public Res() {
        super();
    }

    public Res(T data) {
        super();
        this.data = data;
    }

    public Res(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }

    public Res(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

}
