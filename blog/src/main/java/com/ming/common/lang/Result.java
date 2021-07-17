package com.ming.common.lang;

import com.ming.common.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private Integer code;//状态码
    private String msg;//信息
    private Object data;//返回数据

    //成功
    public static Result success(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(ResponseCode code, Object data) {
        return success(code.getCode(), code.getMsg(), data);
    }

    public static Result success(ResponseCode code, String detail) {
        return success(code.getCode(), detail, null);
    }

    public static Result success(Object data) {
        return success(ResponseCode.SUCCESS, data);
    }

    public static Result success(String msg) {
        return success(ResponseCode.SUCCESS, msg);
    }

    //失败
    public static Result fail(Integer code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(ResponseCode code) {
        return fail(code.getCode(), code.getMsg(), null);
    }

    public static Result fail(ResponseCode code, String detail) {
        return fail(code.getCode(), detail, null);
    }

    public static Result fail(String msg, Object data) {
        return fail(ResponseCode.FAILED.getCode(), msg, data);
    }

    public static Result fail(String msg) {
        return fail(ResponseCode.FAILED.getCode(), msg, null);
    }


}
