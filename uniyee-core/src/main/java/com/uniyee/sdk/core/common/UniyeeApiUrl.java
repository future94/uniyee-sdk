package com.uniyee.sdk.core.common;

import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import lombok.AllArgsConstructor;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 3:11 下午
 */
public interface UniyeeApiUrl {

    /**
     * 得到api完整地址.
     *
     * @param config 微信公众号配置
     * @return api地址
     */
    String getUrl(UniyeeConfigStorage config);

    @AllArgsConstructor
    enum TokenUrl implements UniyeeApiUrl {
        /**
         *
         */
        GET_TOKEN("/token/request"),
        ;
        private final String path;

        @Override
        public String getUrl(UniyeeConfigStorage config) {
            return config.getUrl().concat(path);
        }
    }

    @AllArgsConstructor
    enum VaUrl implements UniyeeApiUrl {

        /**
         *
         */
        VA_CREATE("/virtualAccount/create"),
        ;
        private final String path;

        @Override
        public String getUrl(UniyeeConfigStorage config) {
            return config.getUrl().concat(path);
        }
    }
}
