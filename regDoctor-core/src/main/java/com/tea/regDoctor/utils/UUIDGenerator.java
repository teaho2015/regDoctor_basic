/**
 *
 */
package com.tea.regDoctor.utils;

import java.util.UUID;

public class UUIDGenerator {
    /**
     * 生成32位UUID，不含“-”符
     *
     * @return
     */
    public static final String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    public static void main(String[] args) {
        System.out.println(generateUUID());
    }
}
