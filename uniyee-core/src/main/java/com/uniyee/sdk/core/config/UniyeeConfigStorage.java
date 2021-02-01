package com.uniyee.sdk.core.config;

import com.uniyee.sdk.core.common.UniyeeToken;

import java.util.concurrent.locks.Lock;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:21 上午
 */
public interface UniyeeConfigStorage {

    /**
     * Gets token.
     *
     * @return the token
     */
    String getToken();

    void setToken(String token);

    /**
     * Gets token lock.
     *
     * @return the token lock
     */
    Lock getTokenLock();

    /**
     * Is token expired boolean.
     *
     * @return the boolean
     */
    boolean isTokenExpired();

    /**
     * Gets expires time.
     *
     * @return the expires time
     */
    long getExpiresTime();

    /**
     * Forced to expire token
     */
    void expireToken();

    /**
     * @param uniyeeToken update uniyeeToken object
     */
    void updateAccessToken(UniyeeToken uniyeeToken);

    /**
     * @param token             new token value
     * @param expiresInSeconds  expires seconds
     */
    void updateAccessToken(String token, int expiresInSeconds);

    /**
     * Whether to refresh automatically token
     *
     * @return the boolean
     */
    boolean autoRefreshToken();

    /**
     * Gets merchantNo
     * @return the merchantNo
     */
    String getMerchantNo();

    void setMerchantNo(String merchantNo);

    /**
     * Get secretKey
     * @return the secretKey
     */
    String getSecretKey();

    void setSecretKey(String secretKey);

    String getPrivateKey();

    void setPrivateKey(String privateKey);

    /**
     * Gets http proxy host.
     *
     * @return the http proxy host
     */
    String getHttpProxyHost();

    void setHttpProxyHost(String httpProxyHost);

    /**
     * Gets http proxy port.
     *
     * @return the http proxy port
     */
    int getHttpProxyPort();

    void setHttpProxyPort(int httpProxyPort);

    /**
     * Gets http proxy username.
     *
     * @return the http proxy username
     */
    String getHttpProxyUsername();

    void setHttpProxyUsername(String httpProxyUsername);

    /**
     * Gets http proxy password.
     *
     * @return the http proxy password
     */
    String getHttpProxyPassword();

    void setHttpProxyPassword(String httpProxyPassword);

    String getUrl();

    void setUrl(String url);
}
