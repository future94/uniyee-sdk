package com.uniyee.sdk.core.util.http.apache;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2021/1/27 5:26 下午
 */
public class Utf8ResponseHandler implements ResponseHandler<String> {

    public static final ResponseHandler<String> INSTANCE = new Utf8ResponseHandler();

    @Override
    public String handleResponse(final HttpResponse response) throws IOException {
        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            EntityUtils.consume(entity);
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.toString());
        }
        return entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);
    }

}

