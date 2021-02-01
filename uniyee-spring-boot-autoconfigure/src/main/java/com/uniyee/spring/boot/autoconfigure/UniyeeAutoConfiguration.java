package com.uniyee.spring.boot.autoconfigure;

import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.config.impl.UniyeeDefaultConfig;
import com.uniyee.sdk.core.service.UniyeeService;
import com.uniyee.sdk.core.service.impl.UniyeeServiceHttpClientImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/29 11:14 下午
 */
@Configuration
@EnableConfigurationProperties(UniyeeConfigurationProperties.class)
@ConditionalOnMissingBean(UniyeeService.class)
@ConditionalOnProperty(prefix = "uniyee", name = "enabled", matchIfMissing = true)
public class UniyeeAutoConfiguration {

    private final UniyeeConfigurationProperties uniyeeConfigurationProperties;

    public UniyeeAutoConfiguration(UniyeeConfigurationProperties uniyeeConfigurationProperties) {
        this.uniyeeConfigurationProperties = uniyeeConfigurationProperties;
    }

    @Bean
    @ConditionalOnMissingBean(UniyeeService.class)
    public UniyeeService uniyeeService() {
        List<UniyeeConfigurationProperties.MerchantConfig> merchantConfigList = uniyeeConfigurationProperties.getMerchant();
        if (merchantConfigList.isEmpty()) {
            throw new IllegalArgumentException("请必须配置一个商户参数");
        }
        UniyeeService uniyeeService = new UniyeeServiceHttpClientImpl();
        if (merchantConfigList.size() == 1) {
            uniyeeService.setUniyeeConfigStorage(buildConfig(merchantConfigList.get(0)));
        } else {
            Map<String, UniyeeConfigStorage> configs = merchantConfigList.stream().map(this::buildConfig).collect(Collectors.toMap(UniyeeConfigStorage::getMerchantNo, configStorage -> configStorage));
            uniyeeService.setMultiConfigs(configs, merchantConfigList.get(0).getMerchantNo());
        }
        uniyeeService.setMaxRetryTimes(uniyeeConfigurationProperties.getRequest().getMaxRetryTimes());
        uniyeeService.setRetrySleepMillis(uniyeeConfigurationProperties.getRequest().getRetrySleepMillis());
        return uniyeeService;
    }

    private UniyeeConfigStorage buildConfig(UniyeeConfigurationProperties.MerchantConfig merchantConfig) {
        UniyeeConfigStorage configStorage = new UniyeeDefaultConfig();
        if (StringUtils.isEmpty(merchantConfig.getMerchantNo())) {
            throw new IllegalArgumentException("商户参数merchantNo必填");
        }
        if (StringUtils.isEmpty(merchantConfig.getSecretKey())) {
            throw new IllegalArgumentException("商户参数secretKey必填");
        }
        if (StringUtils.isEmpty(merchantConfig.getPrivateKey())) {
            throw new IllegalArgumentException("商户参数privateKey必填");
        }
        configStorage.setUrl(uniyeeConfigurationProperties.getUrl());
        configStorage.setMerchantNo(merchantConfig.getMerchantNo());
        configStorage.setSecretKey(merchantConfig.getSecretKey());
        configStorage.setPrivateKey(merchantConfig.getPrivateKey());
        configStorage.setToken(merchantConfig.getToken());
        Optional.ofNullable(uniyeeConfigurationProperties.getHttpProxy()).ifPresent( httpProxyConfig -> { configStorage.setHttpProxyHost(httpProxyConfig.getHost());configStorage.setHttpProxyPort(httpProxyConfig.getPort());configStorage.setHttpProxyUsername(httpProxyConfig.getUsername());configStorage.setHttpProxyPassword(httpProxyConfig.getPassword());});
        return configStorage;
    }

}


