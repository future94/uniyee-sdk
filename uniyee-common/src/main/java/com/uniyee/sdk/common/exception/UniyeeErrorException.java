package com.uniyee.sdk.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:10 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UniyeeErrorException extends Exception {

    private UniyeeError error;

    public UniyeeErrorException(UniyeeError error) {
        super(error.toString());
        this.error = error;
    }

    public UniyeeErrorException(UniyeeError error, Throwable cause) {
        super(error.toString(), cause);
        this.error = error;
    }

    public UniyeeError getError() {
        return this.error;
    }
}
