package com.uniyee.sdk.core.result;

import com.uniyee.sdk.core.result.vo.VaCreateResultVo;
import com.uniyee.sdk.common.exception.UniyeeError;
import lombok.Data;

import java.io.Serializable;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:40 上午
 */
@Data
public class VaCreateResult implements Serializable {

    private static final long serialVersionUID = -4441105737636359257L;

    private UniyeeError meta;

    private VaCreateResultVo data;
}
