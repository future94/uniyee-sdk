package com.uniyee.sdk.core.param;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.uniyee.sdk.common.enums.UniyeeEnumJsonAdapter;
import com.uniyee.sdk.common.enums.VaAccountTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/28 11:26 上午
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VaCreateParam extends BaseRequestParam implements Serializable {

    private static final long serialVersionUID = -6277596059035022332L;

    /**
     * 帮申请的商户号
     */
    private String merchantNo;

    /**
     * 账户类型
     */
    @SerializedName("accountType")
    @JsonAdapter(UniyeeEnumJsonAdapter.class)
    private VaAccountTypeEnum accountTypeEnum;

    /**
     * 账户所在地
     */
    private String country;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 电商平台名称
     */
    private String platformName;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺URL地址
     */
    private String shopUrl;

    /**
     * 店铺经营方向
     */
    private String shopBusiness;

    /**
     * 店铺截屏图片
     */
    private String shopPic;
}
