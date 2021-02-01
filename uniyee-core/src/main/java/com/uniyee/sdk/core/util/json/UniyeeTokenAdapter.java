package com.uniyee.sdk.core.util.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.uniyee.sdk.core.common.UniyeeToken;

import java.lang.reflect.Type;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:53 上午
 */
public class UniyeeTokenAdapter implements JsonDeserializer<UniyeeToken> {

    @Override
    public UniyeeToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        UniyeeToken accessToken = new UniyeeToken();
        JsonObject accessTokenJsonObject = jsonElement.getAsJsonObject();

        if (accessTokenJsonObject.get("access_token") != null && !accessTokenJsonObject.get("access_token").isJsonNull()) {
            accessToken.setAccessToken(GsonHelper.getAsString(accessTokenJsonObject.get("access_token")));
        }
        if (accessTokenJsonObject.get("expires_in") != null && !accessTokenJsonObject.get("expires_in").isJsonNull()) {
            accessToken.setExpiresIn(GsonHelper.getAsPrimitiveInt(accessTokenJsonObject.get("expires_in")));
        }
        return accessToken;
    }
}
