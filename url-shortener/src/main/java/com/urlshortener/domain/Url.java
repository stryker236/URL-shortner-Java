package com.urlshortener.domain;

public class Url {

    private String shortCode;
    private String originalUrl;

    public Url(String shortCode, String originalUrl) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}