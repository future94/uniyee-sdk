package com.uniyee.sdk.core.result.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:41 上午
 */
@Data
public class VirtualAccountInfoDto {

    private String globalAccountName;

    private String bankCardNo;

    private String bankName;

    private String bankAddress;

    private String swiftCode;

    private String accountStatus;

    private Date createdTime;

    private Date completeTime;

}
