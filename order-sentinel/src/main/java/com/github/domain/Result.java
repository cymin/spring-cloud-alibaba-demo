package com.github.domain;

/**
 * @author : cymin
 * @date : 2021.11.08 下午6:21
 */
public class Result<T> {
    public Integer code;
    public String msg;
    public T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
    public static Result get(Integer code, String msg) {
        return new Result(code, msg);
    }
}
