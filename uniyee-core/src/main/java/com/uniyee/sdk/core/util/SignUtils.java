package com.uniyee.sdk.core.util;

import com.uniyee.sdk.common.annotation.UniyeeSign;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:46 上午
 */
public class SignUtils {

    public static Map<String, Object> createSign(Object obj) throws IllegalAccessException {
        List<Field> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        if (fields.length > 0) {
            list.addAll(Arrays.asList(fields));
        }

        Class<?> supClz = clz.getSuperclass();
        Field[] supFields = supClz.getDeclaredFields();
        if (supFields.length > 0) {
            list.addAll(Arrays.asList(supFields));
        }

        if (list.size() < 1) {
            return null;
        } else {
            for (Field field : list) {
                if (field.isAnnotationPresent(UniyeeSign.class)) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(obj);
                    if (null != value && "" != value) {
                        map.put(name, value);
                    }
                }
            }

            return map;
        }
    }

    public static String signSort(Map<String, Object> map, String split) {
        if (null != map && !map.isEmpty()) {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<>(map.entrySet());
            infoIds.sort(Map.Entry.comparingByKey());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < infoIds.size(); ++i) {
                Map.Entry<String, Object> item = infoIds.get(i);
                String key = item.getKey();
                Object val = item.getValue();
                if (null != val && "" != val) {
                    if (0 == i) {
                        sb.append(key).append("=").append(val);
                    } else {
                        sb.append(split).append(key).append("=").append(val);
                    }
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}
