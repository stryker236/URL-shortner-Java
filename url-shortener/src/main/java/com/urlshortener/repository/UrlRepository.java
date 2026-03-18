package com.urlshortener.repository;

import com.urlshortener.config.DatabaseConfig;
import com.urlshortener.domain.Url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UrlRepository {
    private String _DATABASE;
    private Connection conn;
    
    public UrlRepository(Connection conn) throws Exception {
        this.conn = conn;
    }

    public void save(Url url) throws Exception {

        String sql = "INSERT INTO urls (short_code, original_url) VALUES (?, ?)";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, url.getCode());
        stmt.setString(2, url.getUrl());

        stmt.executeUpdate();
    }

    public Url findByShortCode(String shortCode) throws Exception {
        Connection conn = DatabaseConfig.getConnection(this._DATABASE);

        String sql = "SELECT original_url FROM urls WHERE short_code = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, shortCode);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String original = rs.getString("original_url");
            conn.close();
            return new Url(shortCode, original);
        }

        conn.close();
        return null;
    }

    public Url findByOriginalUrl(String originalUrl) throws Exception {
        Connection conn = DatabaseConfig.getConnection(this._DATABASE);

        String sql = "SELECT short_code FROM urls WHERE original_url = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, originalUrl);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String code = rs.getString("short_code");
            conn.close();
            return new Url(code, originalUrl);
        }

        conn.close();
        return null;
    }

    public void deleteByShortCode(String shortCode) throws Exception {
        Connection conn = DatabaseConfig.getConnection(this._DATABASE);

        String sql = "DELETE FROM urls WHERE short_code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, shortCode);

        stmt.executeUpdate();
        conn.close();
    }

    public void deleteByOriginalUrl(String originalUrl) throws Exception {
        Connection conn = DatabaseConfig.getConnection(this._DATABASE);

        String sql = "DELETE FROM urls WHERE original_url = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, originalUrl);

        stmt.executeUpdate();
        conn.close();
    }

    public boolean existsByShortCode(String shortCode) throws Exception {
        Connection conn = DatabaseConfig.getConnection(this._DATABASE);

        String sql = "SELECT * FROM urls WHERE short_code = ? LIMIT 1";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, shortCode);

        ResultSet rs = stmt.executeQuery();

        boolean exists = rs.next();

        conn.close();
        return exists;
    }

    public boolean existsByOriginalUrl(String originalUrl) throws Exception {

        String sql = "SELECT * FROM urls WHERE original_url = ? LIMIT 1";
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1, originalUrl);

        ResultSet rs = stmt.executeQuery();

        boolean exists = rs.next();

        return exists;
    }
}