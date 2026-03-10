package com.urlshortener.service;

import com.urlshortener.domain.Url;
import com.urlshortener.repository.UrlRepository;

public class UrlService {

    private UrlRepository repository = new UrlRepository();

    public String createShortUrl(String originalUrl) throws Exception {

        String code = ShortCodeGenerator.generate();

        Url url = new Url(code, originalUrl);

        repository.save(url);

        return code;
    }

    public String getOriginalUrl(String code) throws Exception {

        Url url = repository.findByShortCode(code);

        if (url == null) {
            return null;
        }

        return url.getOriginalUrl();
    }
}