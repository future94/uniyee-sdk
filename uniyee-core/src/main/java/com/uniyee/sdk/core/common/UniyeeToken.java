package com.uniyee.sdk.core.common;

import com.uniyee.sdk.core.util.json.UniyeeGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:29 上午
 */
@Data
public class UniyeeToken implements Serializable {

    private static final long serialVersionUID = -8771422697761450372L;

    private String accessToken;

    private int expiresIn = -1;

    public static UniyeeToken fromJson(String json) {
        return UniyeeGsonBuilder.create().fromJson(json, UniyeeToken.class);
    }
}
