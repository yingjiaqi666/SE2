package com.seecoder.BlueWhale.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultVO<T> implements Serializable {

    private String code;

    private String msg;

    private T data;

    public static <T> ResultVO<T> buildSuccess(T result) {
        return new ResultVO<T>("200", null, result);
    }

    public static <T> ResultVO<T> buildFailure(String msg) {
        return new ResultVO<T>("400", msg, null);
    }

    public static <T> ResultVO<T> buildLoginFailure(T result){ return new ResultVO<T>("401",null,result); }//未登录应该返回401

}