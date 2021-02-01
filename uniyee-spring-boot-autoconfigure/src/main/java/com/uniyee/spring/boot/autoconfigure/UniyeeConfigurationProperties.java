package com.uniyee.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/29 11:09 下午
 */
@ConfigurationProperties(prefix = "uniyee")
public class UniyeeConfigurationProperties {

    private String url = "http://localhost:8083";

    @NestedConfigurationProperty
    private HttpProxyConfig httpProxy;

    @NestedConfigurationProperty
    private List<MerchantConfig> merchant = new ArrayList<>();

    @NestedConfigurationProperty
    private RequestConfig request = new RequestConfig();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpProxyConfig getHttpProxy() {
        return httpProxy;
    }

    public void setHttpProxy(HttpProxyConfig httpProxy) {
        this.httpProxy = httpProxy;
    }

    public List<MerchantConfig> getMerchant() {
        return merchant;
    }

    public void setMerchant(List<MerchantConfig> merchant) {
        this.merchant = merchant;
    }

    public RequestConfig getRequest() {
        return request;
    }

    public void setRequest(RequestConfig request) {
        this.request = request;
    }

    static class HttpProxyConfig {

        private String host;

        private int port;

        private String username;

        private String password;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    static class MerchantConfig {

        private String merchantNo;

        private String secretKey;

        private String privateKey;

        private String token;

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    static class RequestConfig {

        private int retrySleepMillis = 1000;

        private int maxRetryTimes = 3;

        public int getRetrySleepMillis() {
            return retrySleepMillis;
        }

        public void setRetrySleepMillis(int retrySleepMillis) {
            this.retrySleepMillis = retrySleepMillis;
        }

        public int getMaxRetryTimes() {
            return maxRetryTimes;
        }

        public void setMaxRetryTimes(int maxRetryTimes) {
            this.maxRetryTimes = maxRetryTimes;
        }
    }
}
