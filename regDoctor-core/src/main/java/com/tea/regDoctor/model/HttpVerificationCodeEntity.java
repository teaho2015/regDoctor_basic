/**
 * @author teaho2015@gmail.com
 * since 2016
 */
package com.tea.regDoctor.model;

import java.util.Map;

public class HttpVerificationCodeEntity {
    private String result;
    private Map<String, String> cookiesMap;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, String> getCookiesMap() {
        return cookiesMap;
    }

    public void setCookiesMap(Map<String, String> cookiesMap) {
        this.cookiesMap = cookiesMap;
    }
}
