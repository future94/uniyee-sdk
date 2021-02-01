package com.uniyee.sdk.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 4:58 下午
 */
@Getter
@AllArgsConstructor
public enum UniyeeCodeEnum {

    /**
     *
     */
    CODE_0000("0000", "ok"),
    CODE_9021("9021", "token expired"),
    CODE_9020("9020", "invalid token"),
    CODE_9999("9999", "system error"),
    ;

    private String code;

    private String msg;

}
