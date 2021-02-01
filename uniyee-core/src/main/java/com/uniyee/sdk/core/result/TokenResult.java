package com.uniyee.sdk.core.result;

import com.uniyee.sdk.core.result.vo.TokenVo;
import com.uniyee.sdk.common.exception.UniyeeError;
import lombok.Data;

import java.io.Serializable;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 4:31 下午
 */
@Data
public class TokenResult implements Serializable {

    private static final long serialVersionUID = 2314902260836412347L;

    /**
     * base result
     */
    private UniyeeError meta;

    /**
     * business result
     */
    private TokenVo data;
}
