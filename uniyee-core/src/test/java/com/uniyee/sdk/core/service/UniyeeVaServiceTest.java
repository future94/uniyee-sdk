package com.uniyee.sdk.core.service;

import com.uniyee.sdk.core.config.UniyeeConfigStorage;
import com.uniyee.sdk.core.config.impl.UniyeeMemoryConfig;
import com.uniyee.sdk.core.param.VaCreateParam;
import com.uniyee.sdk.core.result.vo.VaCreateResultVo;
import com.uniyee.sdk.core.service.impl.UniyeeServiceHttpClientImpl;
import com.uniyee.sdk.common.enums.VaAccountTypeEnum;
import com.uniyee.sdk.common.exception.SignException;
import com.uniyee.sdk.common.exception.UniyeeErrorException;
import org.junit.Test;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/29 5:58 下午
 */
public class UniyeeVaServiceTest {

    @Test
    public void vaCreate() {
        String merchantNo = "XP00578";
        String secretKey = "nhkpxiuok3i57bgn7jb6is";
        UniyeeConfigStorage config = new UniyeeMemoryConfig();
        config.setMerchantNo(merchantNo);
        config.setSecretKey(secretKey);
        config.setPrivateKey(PRIVATE_KEY);
        UniyeeService service = new UniyeeServiceHttpClientImpl();
        service.addConfig(merchantNo, config);
        VaCreateParam vaCreateParam = new VaCreateParam();
        vaCreateParam.setMerchantNo("XP000369");
        vaCreateParam.setAccountTypeEnum(VaAccountTypeEnum.INV);
        vaCreateParam.setCountry("");
        vaCreateParam.setAccountName("");
        vaCreateParam.setPlatformName("");
        vaCreateParam.setShopName("");
        vaCreateParam.setShopUrl("");
        vaCreateParam.setShopBusiness("");
        vaCreateParam.setShopPic("");
        try {
            VaCreateResultVo vaCreateResultVo = service.getVaService().vaCreate(vaCreateParam);
            System.out.println(vaCreateResultVo);
        } catch (UniyeeErrorException e) {
            e.printStackTrace();
        } catch (SignException e) {
            e.printStackTrace();
        }
    }

    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKbrtoqvjDZLKkBK3/+70tq/FmQzHBXtWlq6bnopIe29EX6njbs3IZEhJrePhxR9h+eX476VQLsWvMYqfzWuknjuM4I0FhLVb45LG4RgWODO0bPSoaZM52AVKRpcSA8wH1hbde9uuhJpNUHcR9mWeEklKbUi5aW1s+CGvBDWzM0TAgMBAAECgYALsXF9L6UD3KoeDOC6On8f4ckAxe/OvWzZ9DtvBViiHd+O3s/38HnCf+8FZuGQluahW6uRm0UIliQtQabdrNgH0bh6Rin7NMDvwImf1wekQEWphlf979+mVdXjSOJ+/mIuvFskXUp5mYJ57IrsUMEAc921E7VviqmOpFw9oZ30SQJBANJqyer6P1OrPt2hdu+0bF4kcykYV4gr0EETqi6816tepDApVtpNPXaMLMxMoXJ7ojoflW07qHEcNQcO2lDZD2cCQQDLFLkHzEo8yD25keWzlbfcPr68993cAhaTjjXdQoLNkTBDgaP8mJxLmHD0uPuaElTmkdmYMYfw1WFzzlXsvkV1AkBKr7jn/NdJfXkc9djfkGz+pXVy2r8HCHYtAyve1wy+OSOSLLo1VkFAuNSV14Veu1+Dvf4t/YT2UQrJbI41MDZ1AkEAjneDNs1CZ1Gyu7R2iH3H9mKuPLNGmUl9vRZPA2HdkYkBatpPyKznOMFAvO3o1DV4CijXlcVpsLKBI1TlQqhY4QJBAJ5pzt9yMwaefh/hLSrvo4crSbVTZr2NU1AzpO9NjJtwqBm5thqzJ4iaZsFX2UDKUtdCgr9d2IVfawtsbhN3A70=";
}
