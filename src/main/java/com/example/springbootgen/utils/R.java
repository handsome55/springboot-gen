package com.example.springbootgen.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.io.Serializable;

/**
 * TODO
 *
 * @author WUBX
 * @date 2023/6/28 16:29
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = -3007300799619117006L;
    public static final int SUCCESS;
    public static final int FAIL;
    private int code;
    private String msg;
    private T data;

    public R() {
    }

    public static <T> R<T> ok() {
        return restResult((Object)null, SUCCESS, "");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "");
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult((Object)null, FAIL, "");
    }

    public static <T> R<T> fail(String msg) {
        return restResult((Object)null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, "");
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult((Object)null, code, msg);
    }

    private static <T> R<T> restResult(Object o, int code, String msg) {
        return (new R()).code(code).data(o).msg(msg);
    }


    public int getCode() {
        return this.code;
    }

    public R<T> code(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public R<T> msg(String msg) {
        this.msg = StringUtils.isNotBlank(msg) ? msg : "";
        return this;
    }

    public T getData() {
        return this.data;
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }

    public String toString() {
        return "R{code=" + this.code + ", msg='" + this.msg + "', data=" + this.data + "}";
    }

    static {
        SUCCESS = 200;
        FAIL = 500;
    }
}
