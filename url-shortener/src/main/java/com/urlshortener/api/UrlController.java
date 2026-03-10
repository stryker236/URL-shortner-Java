package com.urlshortener.api;

import com.urlshortener.service.UrlService;

import static spark.Spark.*;

public class UrlController {

    public static void register() {

        UrlService service = new UrlService();

        post("/urls", (req, res) -> {

            String originalUrl = req.queryParams("url");

            String code = service.createShortUrl(originalUrl);

            return "http://localhost:4567/" + code;
        });

        get("/:code", (req, res) -> {

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