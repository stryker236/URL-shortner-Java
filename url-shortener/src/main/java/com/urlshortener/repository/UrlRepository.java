package com.urlshortener.repository;

import com.urlshortener.config.DatabaseConfig;
import com.urlshortener.domain.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UrlRepository {

    public void save(Url url) throws Exception {

        Connection conn = DatabaseConfig.getConnection();

        String sql = "INSERT INTO urls (short_code, original_url) VALUES (?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, url.getShortCode());
        stmt.setString(2, url.getOriginalUrl());

        stmt.executeUpdate();

        conn.close();
    }

    public Url findByShortCode(String shortCode) throws Exception {
        Connection conn = DatabaseConfig.getConnection();
        
        String sql = "SELECT original_url FROM urls WHERE short_code = ?";
        
        PreparedStatement stmt = conn.prepareStatement(sql); // this is to avoid SQL injection
        stmt.setString(1, shortCode); // this is also to avoid SQL injection
        ResultSet rs = stmt.executeQuery(); // this is the actual query execution

        if (rs.next()) { //
            String original = rs.getString("original_url");
            conn.close();
            return new Url(shortCode, original);
        }

        conn.close();
        return null;
    }

    public boolean existsByShortCode(String shortCode) throws Exception {
        Connection conn = DatabaseConfig.getConnection();

        // this make JOOQ useless but I want to show how to do it with plain JDBC
        String sql = "SELECT * FROM urls WHERE short_code = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, shortCode);
        
        ResultSet rs = stmt.executeQuery();
        
        boolean exists = rs.next();
        
        conn.close();
        return exists;
    }

    public boolean existsByOriginalUrl(String originalUrl) throws Exception {
        Connection conn = DatabaseConfig.getConnection();

        String sql = "SELECT * FROM urls WHERE original_url = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, originalUrl);
        
        ResultSet rs = stmt.executeQuery();
        
        boolean exists = rs.next();
        
        conn.close();
        return exists;
    }
}