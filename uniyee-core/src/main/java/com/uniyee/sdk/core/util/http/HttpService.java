package com.uniyee.sdk.core.util.http;

import com.uniyee.sdk.common.exception.UniyeeErrorException;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 3:42 下午
 */
public interface HttpService {

    /**
     * GET请求.
     *
     * @param queryParam 参数
     * @param url        请求接口地址
     * @return 接口响应字符串
     * @throws UniyeeErrorException 异常
     */
    String get(String url, String queryParam) throws UniyeeErrorException;

    /**
     * POST请求.
     *
     * @param postData 请求参数json值
     * @param url      请求接口地址
     * @return 接口响应字符串
     * @throws UniyeeErrorException 异常
     */
    String post(String url, String postData) throws UniyeeErrorException;

    /**
     * POST请求.
     *
     * @param url 请求接口地址
     * @param obj 请求对象
     * @return 接口响应字符串
     * @throws UniyeeErrorException 异常
     */
    String post(String url, Object obj) throws UniyeeErrorException;

    /**
     * <pre>
     * 公用的
     * 比{@link #get}和{@link #post}方法更灵活，可以自己构造RequestExecutor用来处理不同的参数和不同的返回类型。
     * </pre>
     *
     * @param <T>      the type parameter
     * @param <E>      the type parameter
     * @param executor 执行器
     * @param url      接口地址
     * @param data     参数数据
     * @return 结果
     * @throws UniyeeErrorException 异常
     */
    <T, E> T execute(RequestExecutor<T, E> executor, String url, E data) throws UniyeeErrorException;
}
