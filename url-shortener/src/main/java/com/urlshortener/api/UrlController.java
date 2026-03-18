package com.urlshortener.api;

import com.google.gson.Gson;
import com.urlshortener.config.DatabaseConfig;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.UrlService;

import static spark.Spark.*;

import java.net.URI;
import java.sql.Connection;
public class UrlController {
    private static String DATABASE = "urlshortener";
    
    public static void register() throws Exception {
        Connection conn = DatabaseConfig.getConnection(DATABASE); // Just to check if the connection works
        UrlRepository repo = new UrlRepository(conn);
        UrlService service = new UrlService(repo);

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

    }
}