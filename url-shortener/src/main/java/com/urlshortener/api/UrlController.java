package com.urlshortener.api;

import com.google.gson.Gson;
import com.urlshortener.config.DatabaseConfig;
import com.urlshortener.repository.RedisRepository;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.UrlService;

import static spark.Spark.*;

import java.net.URI;
import java.sql.Connection;

import redis.clients.jedis.Jedis;
import com.urlshortener.config.RedisConfig;

public class UrlController {

    public static void register() throws Exception {
        Connection conn = DatabaseConfig.getConnection();
        Jedis conn2 = RedisConfig.getConnection();

        UrlRepository repo = new UrlRepository(conn);
        RedisRepository redis = new RedisRepository(conn2);
        
        UrlService service = new UrlService(repo, redis);

        Gson gson = new Gson();

        // Create short URL through params
        post("/urls", (req, res) -> {

            String originalUrl = req.queryParams("url").toLowerCase();
            if (!originalUrl.startsWith("http")) {
                originalUrl = "http://" + originalUrl;
            }
            URI uri = URI.create(originalUrl);
            System.out.println("Original URL: " + originalUrl);

            originalUrl = uri.getHost();
            if (uri.getRawPath() != null && !uri.getRawPath().isEmpty()) {
                originalUrl += uri.getRawPath();
            }
            if (uri.getRawQuery() != null && !uri.getRawQuery().isEmpty()) {
                originalUrl += "?" + uri.getRawQuery();
            }
            System.out.println("Original after processing: " + originalUrl);
            String code = service.createShortUrl(originalUrl);
            if (code == null) {
                res.status(400);
                return gson.toJson("URL already exists");
            }
            res.status(201);
            res.type("application/json");
            return "http://localhost:4567/" + code + "/redirect";
        });

        // Get original URL info
        get("/urls/:code", (req, res) -> {
            String code = req.params(":code");
            String original = service.getOriginalUrl(code);

            if (original == null) {
                res.status(404);
                return gson.toJson("Not found");
            }

            res.type("application/json");
            return original;
        });

        // Redirect to original URL
        get("/urls/:code/redirect", (req, res) -> {
            String code = req.params(":code");
            String original = service.getOriginalUrl(code);
            System.out.println("Redirecting code: " + code + " to original: " + original);
            if (original == null) {
                res.status(404);
                return "Not found";
            }

            res.redirect("http://" + original);
            return null;
        });

        delete("/urls/:code", (req, res) -> {
            String code = req.params(":code");
            service.deleteShortUrl(code);
            System.out.println("Deleted short URL with code: " + code);
            res.status(204);
            return null;
        });

        delete("/urls", (req, res) -> {
            String originalUrl = req.queryParams("url").toLowerCase();
            if (!originalUrl.startsWith("http")) {
                originalUrl = "http://" + originalUrl;
            }
            URI uri = URI.create(originalUrl);
            System.out.println("Original URL for deletion: " + originalUrl);

            originalUrl = uri.getHost();
            if (uri.getRawPath() != null && !uri.getRawPath().isEmpty()) {
                originalUrl += uri.getRawPath();
            }
            if (uri.getRawQuery() != null && !uri.getRawQuery().isEmpty()) {
                originalUrl += "?" + uri.getRawQuery();
            }
            System.out.println("Original after processing for deletion: " + originalUrl);

            service.deleteByOriginalUrl(originalUrl);
            res.status(204);
            res.type("application/json");
            return "Deleted short URL with original URL: " + originalUrl;
        });
    }
}