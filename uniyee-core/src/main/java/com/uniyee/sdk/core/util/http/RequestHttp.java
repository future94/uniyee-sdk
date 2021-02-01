package com.uniyee.sdk.core.util.http;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:21 下午
 */
public interface RequestHttp<H , P> {

    /**
     * 返回httpClient.
     *
     * @return 返回httpClient
     */
    H getRequestHttpClient();

    /**
     * 返回httpProxy.
     *
     * @return 返回httpProxy
     */
    P getRequestHttpProxy();

    /**
     * 返回HttpType.
     *
     * @return HttpType
     */
    HttpType getRequestType();
}
