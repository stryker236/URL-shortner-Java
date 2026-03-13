package com.urlshortener;

import com.urlshortener.config.DatabaseConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TestDatabaseSetup {
    private static final String DATABASE = "urlshortener_test";
    
    
    @BeforeAll
    static void createTable() throws Exception {
		
		Connection conn = DatabaseConfig.getConnection(DATABASE);
        PreparedStatement stmt = conn.prepareStatement(
                "DROP TABLE IF EXISTS urls");
        stmt.execute();

        PreparedStatement stmt2 = conn.prepareStatement(
                "CREATE TABLE IF NOT EXISTS urls (" +
                        "id BIGSERIAL PRIMARY KEY, " +
                        "short_code VARCHAR(10) UNIQUE NOT NULL, " +
                        "original_url TEXT NOT NULL, " +
                        "created_at TIMESTAMP DEFAULT NOW()" +
                        ")");
        stmt2.execute();

        PreparedStatement stmt3 = conn.prepareStatement(
                "CREATE INDEX IF NOT EXISTS idx_urls_short_code ON urls(short_code)");
        stmt3.execute();
		conn.close();

    }

    @BeforeEach
    void resetTable() throws Exception {
        Connection conn = DatabaseConfig.getConnection(DATABASE);

        PreparedStatement stmt = conn.prepareStatement(
                "TRUNCATE TABLE urls RESTART IDENTITY");
        stmt.execute();
		conn.close();

    }

}