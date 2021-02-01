package com.uniyee.sdk.core.service;

import com.uniyee.sdk.core.param.VaCreateParam;
import com.uniyee.sdk.core.result.vo.VaCreateResultVo;
import com.uniyee.sdk.common.exception.SignException;
import com.uniyee.sdk.common.exception.UniyeeErrorException;

/**
 * Va账户操作
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:23 上午
 */
public interface UniyeeVaService {

    /**
     * <pre>
     * 创建Va账户
     * @param  vaCreateParam             参数
     * @throws SignException            生成签名异常
     * @throws UniyeeErrorException     请求操作错误异常
     * @return VaCreateResultVo         创建成功返回Va账户信息
     */
    VaCreateResultVo vaCreate(VaCreateParam vaCreateParam) throws UniyeeErrorException, SignException;
}
