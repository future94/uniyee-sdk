package com.uniyee.sdk.core.util.http.apache;

import com.uniyee.sdk.core.util.http.RequestHttp;
import com.uniyee.sdk.core.util.http.SimpleGetRequestExecutor;
import com.uniyee.sdk.common.exception.UniyeeErrorException;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 5:24 下午
 */
public class ApacheSimpleGetRequestExecutor extends SimpleGetRequestExecutor<CloseableHttpClient, HttpHost> {
    public ApacheSimpleGetRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String queryParam) throws UniyeeErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }
        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            return handleResponse(responseContent);
        } finally {
            httpGet.releaseConnection();
        }
    }

}

