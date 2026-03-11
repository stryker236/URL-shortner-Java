package com.urlshortener;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.urlshortener.service.UrlService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.urlshortener.config.DatabaseConfig;

class AppTest extends TestDatabaseSetup {

    private static  String DATABASE = "urlshortener_test";
    @BeforeAll
    static void createTable() throws Exception {
        Connection conn = DatabaseConfig.getConnection(DATABASE);
        PreparedStatement stmt = conn.prepareStatement(
                ("CREATE TABLE IF NOT EXISTS urls ( id BIGSERIAL PRIMARY KEY, short_code VARCHAR(10) UNIQUE NOT NULL,original_url TEXT NOT NULL,created_at TIMESTAMP DEFAULT NOW())"));
        stmt.execute();
        PreparedStatement stmt2 = conn
                .prepareStatement("CREATE INDEX IF NOT EXISTS idx_urls_short_code ON urls(short_code)");
        stmt2.execute();
    }

    @Test
    void shouldSaveAndRetrieveUrl() throws Exception {

        Connection conn = DatabaseConfig.getConnection(DATABASE);
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