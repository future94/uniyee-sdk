package com.uniyee.sdk.core.service.impl;

import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.util.http.HttpType;
import com.uniyee.sdk.core.util.http.apache.ApacheHttpClientBuilder;
import com.uniyee.sdk.core.util.http.apache.DefaultApacheHttpClientBuilder;
import com.uniyee.sdk.common.exception.UniyeeErrorException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import static com.uniyee.sdk.core.common.UniyeeApiUrl.TokenUrl.GET_TOKEN;

/**
 * apache http client 发送请求
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:25 下午
 */
public class UniyeeServiceHttpClientImpl extends BaseUniyeeServiceImpl<CloseableHttpClient, HttpHost> {

    private CloseableHttpClient httpClient;

    private HttpHost httpProxy;

    @Override
    public CloseableHttpClient getRequestHttpClient() {
        return this.httpClient;
    }

    @Override
    public HttpHost getRequestHttpProxy() {
        return this.httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return HttpType.APACHE_HTTP;
    }

    @Override
    public void initHttp() {
        UniyeeConfigStorage configStorage = this.getUniyeeConfigStorage();
        ApacheHttpClientBuilder apacheHttpClientBuilder = DefaultApacheHttpClientBuilder.get();
        apacheHttpClientBuilder.httpProxyHost(configStorage.getHttpProxyHost())
                .httpProxyPort(configStorage.getHttpProxyPort())
                .httpProxyUsername(configStorage.getHttpProxyUsername())
                .httpProxyPassword(configStorage.getHttpProxyPassword());

        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() > 0) {
            this.httpProxy = new HttpHost(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort());
        }

        this.httpClient = apacheHttpClientBuilder.build();
    }

    @Override
    public String getToken(boolean forceRefresh) throws UniyeeErrorException {
        final UniyeeConfigStorage config = this.getUniyeeConfigStorage();
        if (!config.isTokenExpired() && !forceRefresh) {
            return config.getToken();
        }

        Lock lock = config.getTokenLock();
        boolean locked = false;
        try {
            do {
                locked = lock.tryLock(100, TimeUnit.MILLISECONDS);
                if (!forceRefresh && !config.isTokenExpired()) {
                    return config.getToken();
                }
            } while (!locked);

            try {
                if (StringUtils.isEmpty(config.getMerchantNo())) {
                    throw new IllegalArgumentException("merchantNo不能为空");
                }
                if (StringUtils.isEmpty(config.getSecretKey())) {
                    throw new IllegalArgumentException("secretKey不能为空");
                }
                HttpPost httpPost = new HttpPost(GET_TOKEN.getUrl(config));
                Map<String, String> params = new HashMap<String, String>() {{
                    put("merchantNo", config.getMerchantNo());
                    put("secretKey", config.getSecretKey());
                }};
                if (this.getRequestHttpProxy() != null) {
                    RequestConfig requestConfig = RequestConfig.custom().setProxy(this.getRequestHttpProxy()).build();
                    httpPost.setConfig(requestConfig);
                }
                List<NameValuePair> reqParams = new ArrayList<>();
                for (Map.Entry<String, String> e : params.entrySet()) {
                    reqParams.add(new BasicNameValuePair(e.getKey(), e.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(reqParams, "UTF-8"));
                try (CloseableHttpResponse response = getRequestHttpClient().execute(httpPost)) {
                    return this.extractToken(new BasicResponseHandler().handleResponse(response));
                } finally {
                    httpPost.releaseConnection();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }


}
