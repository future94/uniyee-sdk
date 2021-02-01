package com.uniyee.sdk.core.util.http.apache;

import com.google.gson.Gson;
import com.uniyee.sdk.core.result.TokenResult;
import com.uniyee.sdk.core.util.http.RequestExecutor;
import com.uniyee.sdk.core.util.http.RequestHttp;
import com.uniyee.sdk.core.util.http.ResponseHandler;
import com.uniyee.sdk.common.enums.UniyeeCodeEnum;
import com.uniyee.sdk.common.exception.UniyeeError;
import com.uniyee.sdk.common.exception.UniyeeErrorException;

import java.io.IOException;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 5:37 下午
 */
public abstract class SimplePostRequestExecutor <H, P> implements RequestExecutor<String, String> {
    protected RequestHttp<H, P> requestHttp;

    public SimplePostRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler)
            throws UniyeeErrorException, IOException {
        handler.handle(this.execute(uri, data));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheSimplePostRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }

    public String handleResponse(String responseContent) throws UniyeeErrorException {
        if (responseContent.isEmpty()) {
            throw new UniyeeErrorException(UniyeeError.builder().success(false).code(UniyeeCodeEnum.CODE_9999.getCode()).message("无响应内容").build());
        }
        if (responseContent.startsWith("<xml>")) {
            //xml格式输出直接返回
            return responseContent;
        }
        TokenResult tokenResult = new Gson().fromJson(responseContent, TokenResult.class);
        if (tokenResult.getMeta() == null) {
            throw new RuntimeException("获取请求响应结果失败, meta is null");
        }
        UniyeeError error = tokenResult.getMeta();
        if (!error.ok()) {
            throw new UniyeeErrorException(error);
        }
        return responseContent;
    }
}

