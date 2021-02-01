package com.uniyee.sdk.core.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uniyee.sdk.core.common.UniyeeToken;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:50 上午
 */
public class UniyeeGsonBuilder {

    private static final GsonBuilder INSTANCE = new GsonBuilder();

    static {
        INSTANCE.disableHtmlEscaping();
        INSTANCE.registerTypeAdapter(UniyeeToken.class, new UniyeeTokenAdapter());
    }

    public static Gson create() {
        return INSTANCE.create();
    }
}
