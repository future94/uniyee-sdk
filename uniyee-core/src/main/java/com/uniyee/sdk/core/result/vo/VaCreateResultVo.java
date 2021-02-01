package com.uniyee.sdk.core.result.vo;

import com.uniyee.sdk.core.result.dto.VirtualAccountInfoDto;
import lombok.Data;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:39 上午
 */
@Data
public class VaCreateResultVo {

    private String status;

    private String overseasAccountNo;

    private VirtualAccountInfoDto virtualAccountInfo;
}
