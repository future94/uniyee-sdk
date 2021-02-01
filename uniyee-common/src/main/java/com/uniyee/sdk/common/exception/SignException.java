package com.uniyee.sdk.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 1:49 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class SignException extends Exception {

    protected String message;

    public SignException(String message) {
        super(message);
        this.message = message;
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
