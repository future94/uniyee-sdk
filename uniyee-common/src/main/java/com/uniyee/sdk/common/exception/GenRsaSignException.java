package com.uniyee.sdk.common.exception;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 1:58 下午
 */
public class GenRsaSignException extends SignException{

    public GenRsaSignException(String message) {
        super(message);
        this.message = message;
    }
}
