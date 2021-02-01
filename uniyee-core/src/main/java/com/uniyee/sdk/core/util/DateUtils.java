package com.uniyee.sdk.core.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 2:07 下午
 */
public class DateUtils {

    public static long getGmt() {
        return LocalDateTime.now().atZone(ZoneId.of("GMT+16:00")).toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
        System.out.println(getGmt());
    }

}
