package com.uniyee.sdk.core.service;

import com.uniyee.sdk.core.common.UniyeeApiUrl;
import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.param.BaseRequestParam;
import com.uniyee.sdk.core.util.http.HttpService;
import com.uniyee.sdk.common.exception.SignException;
import com.uniyee.sdk.common.exception.UniyeeErrorException;

import java.util.Map;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:09 下午
 */
public interface UniyeeService extends HttpService {

    UniyeeVaService getVaService();

    /**
     * 获取access_token, 不强制刷新token.
     *
     * @return token
     * @throws UniyeeErrorException .
     * @see #getToken(boolean)
     */
    String getToken() throws UniyeeErrorException;

    void buildBaseParam(BaseRequestParam param) throws UniyeeErrorException, SignException;

    String getSign(Object param) throws SignException;

    /**
     * 获取token，本方法线程安全.且在多线程同时刷新时只刷新一次
     *
     * 另：本service的所有方法都会在access_token过期时调用此方法
     *
     * 在非必要情况下尽量不要主动调用此方法
     *
     * @param forceRefresh 是否强制刷新
     * @return token
     * @throws UniyeeErrorException .
     */
    String getToken(boolean forceRefresh) throws UniyeeErrorException;

    /**
     * 初始化http请求对象.
     */
    void initHttp();

    /**
     * 获取UniyeeConfigStorage 对象.
     *
     * @return UniyeeConfigStorage
     */
    UniyeeConfigStorage getUniyeeConfigStorage();

    void setUniyeeConfigStorage(UniyeeConfigStorage uniyeeConfigStorage);

    void setMultiConfigs(Map<String, UniyeeConfigStorage> configs, String defaultMerchantNo);

    void addConfig(String merchantNo, UniyeeConfigStorage configStorages);

    void removeConfig(String merchantNo);

    UniyeeService switchoverTo(String merchantNo);

    boolean switchover(String merchantNo);

    void setRetrySleepMillis(int retrySleepMillis);

    void setMaxRetryTimes(int maxRetryTimes);

    String get(UniyeeApiUrl url, String queryParam) throws UniyeeErrorException;

    String post(UniyeeApiUrl url, String postData) throws UniyeeErrorException;

    String post(UniyeeApiUrl url, Object obj) throws UniyeeErrorException;
}
