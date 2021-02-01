package com.uniyee.sdk.core.util.http;

import com.google.gson.Gson;
import com.uniyee.sdk.core.result.TokenResult;
import com.uniyee.sdk.core.util.http.apache.ApacheSimpleGetRequestExecutor;
import com.uniyee.sdk.common.exception.UniyeeError;
import com.uniyee.sdk.common.exception.UniyeeErrorException;

import java.io.IOException;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 5:12 下午
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {

    protected RequestHttp<H, P> requestHttp;

    public SimpleGetRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler) throws UniyeeErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheSimpleGetRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }

    protected String handleResponse(String responseContent) throws UniyeeErrorException {
        TokenResult tokenResult = new Gson().fromJson(responseContent, TokenResult.class);
        if (tokenResult.getMeta() == null) {
            throw new RuntimeException("获取Token请求响应结果失败, meta is null");
        }
        UniyeeError error = tokenResult.getMeta();
        if (!error.ok()) {
            throw new UniyeeErrorException(error);
        }
        return responseContent;
    }
}
