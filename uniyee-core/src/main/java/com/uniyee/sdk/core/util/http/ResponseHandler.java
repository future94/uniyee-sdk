package com.uniyee.sdk.core.util.http;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 5:22 下午
 */
public interface ResponseHandler<T> {

    void handle(T t);
}
