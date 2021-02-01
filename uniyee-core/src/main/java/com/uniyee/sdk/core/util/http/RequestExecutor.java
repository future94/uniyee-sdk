package com.uniyee.sdk.core.util.http;

import com.uniyee.sdk.common.exception.UniyeeErrorException;

import java.io.IOException;

/**
 * http请求执行器.
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 3:38 下午
 */
public interface RequestExecutor<T, E> {

    /**
     * 执行http请求.
     *
     * @param uri    uri
     * @param data   数据
     * @return 响应结果
     * @throws UniyeeErrorException 自定义异常
     * @throws IOException      io异常
     */
    T execute(String uri, E data) throws UniyeeErrorException, IOException;

    /**
     * 执行http请求.
     *
     * @param uri     uri
     * @param data    数据
     * @param handler http响应处理器
     * @throws UniyeeErrorException 自定义异常
     * @throws IOException      io异常
     */
    void execute(String uri, E data, ResponseHandler<T> handler) throws UniyeeErrorException, IOException;
}

