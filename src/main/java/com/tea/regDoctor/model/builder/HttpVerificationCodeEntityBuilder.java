/**
 * Created by 庭亮 on 2016/9/18.
 */
package com.tea.regDoctor.model.builder;

import com.tea.regDoctor.Builder;
import com.tea.regDoctor.model.HttpVerificationCodeEntity;

import java.util.HashMap;
import java.util.Map;

public class HttpVerificationCodeEntityBuilder implements Builder<HttpVerificationCodeEntity> {
    private String result;
    private Map<String, String> cookiesMap;

    public static HttpVerificationCodeEntityBuilder newBuilder() {
        return new HttpVerificationCodeEntityBuilder();
    }

    public HttpVerificationCodeEntityBuilder result(String result) {
        this.result = result;
        return this;
    }

    public HttpVerificationCodeEntityBuilder cookiesMap(Map<String, String> cookiesMap) {
        this.cookiesMap = cookiesMap;
        return this;
    }

    @Override
    public HttpVerificationCodeEntity build() {
        HttpVerificationCodeEntity hvce = new HttpVerificationCodeEntity();
        hvce.setResult(result);
        hvce.setCookiesMap((cookiesMap != null) ? cookiesMap : new HashMap<String, String>());
        return hvce;
    }
}
