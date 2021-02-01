package com.uniyee.sdk.core.service.impl;

import com.google.gson.Gson;
import com.uniyee.sdk.core.common.UniyeeApiUrl;
import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.param.BaseRequestParam;
import com.uniyee.sdk.core.result.TokenResult;
import com.uniyee.sdk.core.result.vo.TokenVo;
import com.uniyee.sdk.core.service.UniyeeService;
import com.uniyee.sdk.core.service.UniyeeVaService;
import com.uniyee.sdk.core.util.DateUtils;
import com.uniyee.sdk.core.util.RsaUtils;
import com.uniyee.sdk.core.util.SignUtils;
import com.uniyee.sdk.core.util.UniyeeConfigStorageHolder;
import com.uniyee.sdk.core.util.http.RequestExecutor;
import com.uniyee.sdk.core.util.http.RequestHttp;
import com.uniyee.sdk.core.util.http.SimpleGetRequestExecutor;
import com.uniyee.sdk.core.util.http.apache.SimplePostRequestExecutor;
import com.uniyee.sdk.common.enums.LanguageEnum;
import com.uniyee.sdk.common.enums.UniyeeCodeEnum;
import com.uniyee.sdk.common.exception.GenRsaSignException;
import com.uniyee.sdk.common.exception.GetSignMapException;
import com.uniyee.sdk.common.exception.SignException;
import com.uniyee.sdk.common.exception.UniyeeError;
import com.uniyee.sdk.common.exception.UniyeeErrorException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 2:19 下午
 */
@Slf4j
public abstract class BaseUniyeeServiceImpl<H, P> implements UniyeeService, RequestHttp<H, P> {

    private final UniyeeVaService vaService = new UniyeeVaServiceImpl(this);

    private Map<String, UniyeeConfigStorage> configStorageMap;

    private int retrySleepMillis = 1000;

    private int maxRetryTimes = 3;

    @Override
    public UniyeeVaService getVaService() {
        return vaService;
    }

    @Override
    public String getToken() throws UniyeeErrorException {
        return getToken(false);
    }

    @Override
    public void buildBaseParam(BaseRequestParam param) throws UniyeeErrorException, SignException {
        if (param == null) {
            throw new NullPointerException();
        }
        if (Objects.equals(param.getLanguage(), null)) {
            param.setLanguage(LanguageEnum.EN);
        }
        if (StringUtils.isEmpty(param.getToken())) {
            param.setToken(this.getToken());
        }
        if (Objects.equals(param.getTimeStamp(), null)) {
            param.setTimeStamp(DateUtils.getGmt());
        }
        if (StringUtils.isEmpty(param.getSign())) {
            param.setSign(getSign(param));
        }

    }

    @Override
    public String getSign(Object param) throws SignException {
        if (param == null) {
            throw new NullPointerException();
        }
        Map<String, Object> mapForSign;
        try {
            mapForSign = SignUtils.createSign(param);
        } catch (IllegalAccessException e) {
            throw new GetSignMapException(e.getMessage());
        }
        String rawString = SignUtils.signSort(mapForSign,"&");
        if (StringUtils.isEmpty(rawString)) {
            throw new NullPointerException();
        }
        String sign;
        try {
            sign = RsaUtils.sign(rawString.getBytes(), this.getUniyeeConfigStorage().getPrivateKey());
        } catch (Exception e) {
            throw new GenRsaSignException(e.getMessage());
        }
        return sign;
    }

    protected String extractToken(String resultContent) throws UniyeeErrorException {
        UniyeeConfigStorage config = this.getUniyeeConfigStorage();
        TokenResult tokenResult = new Gson().fromJson(resultContent, TokenResult.class);
        if (tokenResult.getMeta() == null) {
            throw new RuntimeException("获取请求响应结果失败, meta is null");
        }
        if (!tokenResult.getMeta().isSuccess() || !UniyeeCodeEnum.CODE_0000.getCode().equals(tokenResult.getMeta().getCode())) {
            throw new UniyeeErrorException(tokenResult.getMeta());
        }
        TokenVo tokenVo = tokenResult.getData();
        config.updateAccessToken(tokenVo.getToken(), 3000);
        return config.getToken();
    }

    @Override
    public UniyeeConfigStorage getUniyeeConfigStorage() {
        if (this.configStorageMap.size() == 1) {
            // 只有一个商户，直接返回其配置即可
            return this.configStorageMap.values().iterator().next();
        }
        return this.configStorageMap.get(UniyeeConfigStorageHolder.get());
    }

    @Override
    public void setUniyeeConfigStorage(UniyeeConfigStorage config) {
        final String merchantNo = config.getMerchantNo();
        this.setMultiConfigs( new HashMap<String, UniyeeConfigStorage>(){{put(merchantNo, config);}}, merchantNo);
    }

    @Override
    public void setMultiConfigs(Map<String, UniyeeConfigStorage> configs, String defaultMerchantNo) {
        this.configStorageMap = new HashMap<>(configs);
        UniyeeConfigStorageHolder.set(defaultMerchantNo);
        this.initHttp();
    }

    @Override
    public void addConfig(String merchantNo, UniyeeConfigStorage configStorages) {
        synchronized (this) {
            if (this.configStorageMap == null) {
                this.setUniyeeConfigStorage(configStorages);
            } else {
                this.configStorageMap.put(merchantNo, configStorages);
            }
        }
    }

    @Override
    public void removeConfig(String merchantNo) {
        synchronized (this) {
            if (this.configStorageMap.size() == 1) {
                this.configStorageMap.remove(merchantNo);
                log.warn("已删除最后一个配置：{}，须立即使用setWxMaConfig或setMultiConfigs添加配置", merchantNo);
                return;
            }
            if (UniyeeConfigStorageHolder.get().equals(merchantNo)) {
                this.configStorageMap.remove(merchantNo);
                final String defaultMpId = this.configStorageMap.keySet().iterator().next();
                UniyeeConfigStorageHolder.set(defaultMpId);
                log.warn("已删除默认配置，商户【{}】被设为默认配置", defaultMpId);
                return;
            }
            this.configStorageMap.remove(merchantNo);
        }
    }

    @Override
    public UniyeeService switchoverTo(String merchantNo) {
        if (this.configStorageMap.containsKey(merchantNo)) {
            UniyeeConfigStorageHolder.set(merchantNo);
            return this;
        }
        throw new RuntimeException(String.format("无法找到对应【%s】的商户号配置信息，请核实！", merchantNo));
    }

    @Override
    public boolean switchover(String merchantNo) {
        if (this.configStorageMap.containsKey(merchantNo)) {
            UniyeeConfigStorageHolder.set(merchantNo);
            return true;
        }
        log.error("无法找到对应【{}】的商户号配置信息，请核实！", merchantNo);
        return false;
    }

    @Override
    public void setRetrySleepMillis(int retrySleepMillis) {
        this.retrySleepMillis = retrySleepMillis;
    }

    @Override
    public void setMaxRetryTimes(int maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    @Override
    public <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws UniyeeErrorException {
        int retryTimes = 0;
        do {
            try {
                return this.executeInternal(executor, uri, data);
            } catch (IOException e) {
                if (retryTimes + 1 > this.maxRetryTimes) {
                    log.warn("重试达到最大次数【{}】", maxRetryTimes);
                    //最后一次重试失败后，直接抛出异常，不再等待
                    throw new UniyeeErrorException(UniyeeError.builder()
                            .success(false)
                            .code(UniyeeCodeEnum.CODE_9999.getCode())
                            .message("服务端异常，超出重试次数！")
                            .build());
                }

                // 系统繁忙, 1000ms后重试
                int sleepMillis = this.retrySleepMillis * (1 << retryTimes);
                try {
                    log.warn("系统繁忙，{} ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                }
            }
        } while (retryTimes++ < this.maxRetryTimes);

        log.warn("重试达到最大次数【{}】", this.maxRetryTimes);
        throw new RuntimeException("服务端异常，超出重试次数");
    }

    protected <T, E> T executeInternal(RequestExecutor<T, E> executor, String uri, E data) throws UniyeeErrorException, IOException {
        String token = getToken(false);
        try {
            T result = executor.execute(uri, data);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, data, result);
            return result;
        } catch (UniyeeErrorException e) {
            UniyeeError error = e.getError();
            if (UniyeeCodeEnum.CODE_9020.getCode().equals(error.getCode()) || UniyeeCodeEnum.CODE_9021.getCode().equals(error.getCode())) {
                // 强制设置UniyeeConfigStorage它的token过期了，这样在下一次请求里就会刷新access token
                Lock lock = this.getUniyeeConfigStorage().getTokenLock();
                lock.lock();
                try {
                    if (StringUtils.equals(this.getUniyeeConfigStorage().getToken(), token)) {
                        this.getUniyeeConfigStorage().expireToken();
                    }
                } catch (Exception ex) {
                    this.getUniyeeConfigStorage().expireToken();
                } finally {
                    lock.unlock();
                }
                if (this.getUniyeeConfigStorage().autoRefreshToken()) {
                    log.warn("即将重新获取新的token，错误代码：{}，错误信息：{}", error.getCode(), error.getMessage());
                    return this.execute(executor, uri, data);
                }
            }

            if (!error.ok()) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, data, error);
                throw new UniyeeErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, data, e.getMessage());
            throw new IOException(e.getMessage(), e);
        }
    }

    @Override
    public String get(String url, String queryParam) throws UniyeeErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String get(UniyeeApiUrl url, String queryParam) throws UniyeeErrorException {
        return this.get(url.getUrl(this.getUniyeeConfigStorage()), queryParam);
    }

    @Override
    public String post(String url, String postData) throws UniyeeErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }

    @Override
    public String post(UniyeeApiUrl url, String postData) throws UniyeeErrorException {
        return this.post(url.getUrl(this.getUniyeeConfigStorage()), postData);
    }

    @Override
    public String post(UniyeeApiUrl url, Object obj) throws UniyeeErrorException {
        return this.post(url.getUrl(this.getUniyeeConfigStorage()), obj);
    }

    @Override
    public String post(String url, Object obj) throws UniyeeErrorException {
        return this.execute(SimplePostRequestExecutor.create(this), url, new Gson().toJson(obj));
    }
}
