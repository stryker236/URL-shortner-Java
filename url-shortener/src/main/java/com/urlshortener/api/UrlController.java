package com.urlshortener.api;

import com.google.gson.Gson;
import com.urlshortener.service.UrlService;

import static spark.Spark.*;

public class UrlController {

    public static void register() {

        UrlService service = new UrlService();
        Gson gson = new Gson();

        // Create short URL
        post("/urls", (req, res) -> {

            String originalUrl = req.queryParams("url");
            String code = service.createShortUrl(originalUrl);
            if (code == null) {
                res.status(400);
                return gson.toJson("URL already exists");
            }
            res.status(201);
            res.type("application/json");
            return "http://localhost:4567/r/" + code;
        });

        // Get URL info
        get("/urls/:code", (req, res) -> {

            String code = req.params(":code");
            String original = service.getOriginalUrl(code);

            if (original == null) {
                res.status(404);
                return gson.toJson("Not found");
            }

            res.type("application/json");
            return "http://localhost:4567/r/" + code;
        });

        // Redirect
        get("/r/:code", (req, res) -> {

            String code = req.params(":code");
            String original = service.getOriginalUrl(code);

            if (original == null) {
                res.status(404);
                return "Not found";
            }

            res.redirect(original);
            return null;
        });
    }
}