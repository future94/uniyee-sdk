package com.uniyee.sdk.common.enums;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 3:07 下午
 */
@Slf4j
public class UniyeeEnumJsonAdapter<T extends Enum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.name());
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            String name = json.getAsString();
            Class<T> enumClass = (Class<T>) Class.forName(typeOfT.getTypeName());
            return Enum.valueOf(enumClass, name);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class" + typeOfT.getTypeName() + " not found");
        }
    }
}
