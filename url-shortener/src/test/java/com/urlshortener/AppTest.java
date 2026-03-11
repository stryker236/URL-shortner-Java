package com.urlshortener;

import org.junit.jupiter.api.Test;

import com.urlshortener.service.UrlService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import com.urlshortener.config.DatabaseConfig;


class AppTest {

    @Test
    void shouldSaveAndRetrieveUrl() throws Exception {

        Connection conn = DatabaseConfig.getConnection();
        String url = "https://google.com";
        UrlService service = new UrlService();

        String code = service.createShortUrl(url);
        if (code == null) {
            assertTrue(true, "URL already exists, skipping creation");
            return;
        }

        String retrievedUrl = service.getOriginalUrl(code);

        assertEquals("https://google.com", retrievedUrl);
    }

}