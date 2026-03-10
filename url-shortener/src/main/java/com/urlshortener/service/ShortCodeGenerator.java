package com.urlshortener.service;

import java.util.UUID;

public class ShortCodeGenerator {

    public static String generate() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 6);
    }
}