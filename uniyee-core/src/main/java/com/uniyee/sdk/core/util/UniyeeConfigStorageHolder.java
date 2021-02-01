package com.uniyee.sdk.core.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:34 下午
 */
@Slf4j
public class UniyeeConfigStorageHolder {

    private final static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>() {
        @Override
        protected String initialValue() {
            return "default";
        }
    };

    public static String get() {
        return THREAD_LOCAL.get();
    }

    public static void set(String label) {
        THREAD_LOCAL.set(label);
    }

    /**
     * 此方法需要用户根据自己程序代码，在适当位置手动触发调用，本SDK里无法判断调用时机
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
