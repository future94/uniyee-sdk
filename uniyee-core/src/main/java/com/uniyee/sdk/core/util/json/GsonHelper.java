package com.uniyee.sdk.core.util.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:54 上午
 */
public class GsonHelper {

    public static boolean isNull(JsonElement element) {
        return element == null || element.isJsonNull();
    }

    public static boolean isNotNull(JsonElement element) {
        return !isNull(element);
    }

    public static Long getLong(JsonObject json, String property) {
        return getAsLong(json.get(property));
    }

    public static long getPrimitiveLong(JsonObject json, String property) {
        return getAsPrimitiveLong(json.get(property));
    }

    public static Integer getInteger(JsonObject json, String property) {
        return getAsInteger(json.get(property));
    }

    public static int getPrimitiveInteger(JsonObject json, String property) {
        return getAsPrimitiveInt(json.get(property));
    }

    public static Double getDouble(JsonObject json, String property) {
        return getAsDouble(json.get(property));
    }

    public static double getPrimitiveDouble(JsonObject json, String property) {
        return getAsPrimitiveDouble(json.get(property));
    }

    public static Float getFloat(JsonObject json, String property) {
        return getAsFloat(json.get(property));
    }

    public static float getPrimitiveFloat(JsonObject json, String property) {
        return getAsPrimitiveFloat(json.get(property));
    }

    public static Boolean getBoolean(JsonObject json, String property) {
        return getAsBoolean(json.get(property));
    }

    public static String getString(JsonObject json, String property) {
        return getAsString(json.get(property));
    }

    public static String getAsString(JsonElement element) {
        return isNull(element) ? null : element.getAsString();
    }

    public static Long getAsLong(JsonElement element) {
        return isNull(element) ? null : element.getAsLong();
    }

    public static long getAsPrimitiveLong(JsonElement element) {
        Long r = getAsLong(element);
        return r == null ? 0L : r;
    }

    public static Integer getAsInteger(JsonElement element) {
        return isNull(element) ? null : element.getAsInt();
    }

    public static int getAsPrimitiveInt(JsonElement element) {
        Integer r = getAsInteger(element);
        return r == null ? 0 : r;
    }

    public static Boolean getAsBoolean(JsonElement element) {
        return isNull(element) ? null : element.getAsBoolean();
    }

    public static boolean getAsPrimitiveBool(JsonElement element) {
        Boolean r = getAsBoolean(element);
        return r != null && r;
    }

    public static Double getAsDouble(JsonElement element) {
        return isNull(element) ? null : element.getAsDouble();
    }

    public static double getAsPrimitiveDouble(JsonElement element) {
        Double r = getAsDouble(element);
        return r == null ? 0d : r;
    }

    public static Float getAsFloat(JsonElement element) {
        return isNull(element) ? null : element.getAsFloat();
    }

    public static float getAsPrimitiveFloat(JsonElement element) {
        Float r = getAsFloat(element);
        return r == null ? 0f : r;
    }

    public static JsonArray getAsJsonArray(JsonElement element) {
        return element == null ? null : element.getAsJsonArray();
    }
}
