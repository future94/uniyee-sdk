package com.uniyee.sdk.core.param;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.uniyee.sdk.common.annotation.UniyeeSign;
import com.uniyee.sdk.common.enums.LanguageEnum;
import com.uniyee.sdk.common.enums.UniyeeEnumJsonAdapter;
import lombok.Data;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 2:34 下午
 */
@Data
public abstract class BaseRequestParam {

    /**
     * token
     */
    @UniyeeSign
    private String token;

    /**
     * base on GMT timeStamp
     */
    @UniyeeSign
    private Long timeStamp;

    /**
     * request language CN/EN
     */
    @SerializedName("language")
    @JsonAdapter(UniyeeEnumJsonAdapter.class)
    private LanguageEnum language;

    /**
     * sign
     */
    private String sign;
}
