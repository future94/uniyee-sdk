package com.uniyee.sdk.core.config.impl;

import com.uniyee.sdk.core.common.UniyeeToken;
import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.util.json.UniyeeGsonBuilder;
import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Memory
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 11:32 上午
 */
@Data
public class UniyeeMemoryConfig implements UniyeeConfigStorage {

    protected volatile String merchantNo;

    private volatile String secretKey;

    private volatile String privateKey;

    protected volatile String token;

    private volatile long expiresTime;

    protected volatile String httpProxyHost;

    protected volatile int httpProxyPort;

    protected volatile String httpProxyUsername;

    protected volatile String httpProxyPassword;

    protected volatile String url = "http://localhost:8083";

    protected Lock tokenLock = new ReentrantLock();

    /**
     * 会过期的数据提前过期时间，默认预留200秒的时间
     */
    protected long expiresAheadInMillis(int expiresInSeconds) {
        return System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    /**
     * 判断 expiresTime 是否已经过期
     */
    protected boolean isExpired(long expiresTime) {
        return System.currentTimeMillis() > expiresTime;
    }

    @Override
    public boolean isTokenExpired() {
        return System.currentTimeMillis() > this.expiresTime;
    }

    @Override
    public void expireToken() {
        this.expiresTime = 0;
    }

    @Override
    public synchronized void updateAccessToken(UniyeeToken uniyeeToken) {
        updateAccessToken(uniyeeToken.getAccessToken(), uniyeeToken.getExpiresIn());
    }

    @Override
    public void updateAccessToken(String token, int expiresInSeconds) {
        setToken(token);
        setExpiresTime(expiresAheadInMillis(expiresInSeconds));
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    @Override
    public String toString() {
        return UniyeeGsonBuilder.create().toJson(this);
    }
}
