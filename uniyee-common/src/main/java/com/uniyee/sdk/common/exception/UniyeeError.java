package com.uniyee.sdk.common.exception;

import com.uniyee.sdk.common.enums.UniyeeCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:11 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UniyeeError implements Serializable {

    private static final long serialVersionUID = 2459082923884941137L;

    /**
     * 请求状态
     */
    private boolean success;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    public boolean ok() {
        return UniyeeCodeEnum.CODE_0000.getCode().equals(code);
    }
}
