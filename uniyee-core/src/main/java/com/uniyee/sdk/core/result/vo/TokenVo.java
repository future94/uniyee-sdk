package com.uniyee.sdk.core.result.vo;

import lombok.Data;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 4:33 下午
 */
@Data
public class TokenVo {

    private String token;

    /**
     * token expire time ,base on the
     */
    private Long expireTime;
}
