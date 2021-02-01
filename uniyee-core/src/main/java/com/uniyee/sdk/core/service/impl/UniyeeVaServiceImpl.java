package com.uniyee.sdk.core.service.impl;

import com.google.gson.Gson;
import com.uniyee.sdk.core.common.UniyeeApiUrl;
import com.uniyee.sdk.core.param.VaCreateParam;
import com.uniyee.sdk.core.result.VaCreateResult;
import com.uniyee.sdk.core.result.vo.VaCreateResultVo;
import com.uniyee.sdk.core.service.UniyeeService;
import com.uniyee.sdk.core.service.UniyeeVaService;
import com.uniyee.sdk.common.exception.SignException;
import com.uniyee.sdk.common.exception.UniyeeError;
import com.uniyee.sdk.common.exception.UniyeeErrorException;
import lombok.AllArgsConstructor;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:23 上午
 */
@AllArgsConstructor
public class UniyeeVaServiceImpl implements UniyeeVaService {

    private final UniyeeService uniyeeService;

    @Override
    public VaCreateResultVo vaCreate(VaCreateParam vaCreateParam) throws UniyeeErrorException, SignException {
        uniyeeService.buildBaseParam(vaCreateParam);
        String responseContent = this.uniyeeService.post(UniyeeApiUrl.VaUrl.VA_CREATE, vaCreateParam);
        VaCreateResult vaCreateResult = new Gson().fromJson(responseContent, VaCreateResult.class);
        if (vaCreateResult.getMeta() == null) {
            throw new RuntimeException("获取创建Va请求响应结果失败, meta is null");
        }
        UniyeeError error = vaCreateResult.getMeta();
        if (!error.ok()) {
            throw new UniyeeErrorException(error);
        }
        return vaCreateResult.getData();
    }
}
