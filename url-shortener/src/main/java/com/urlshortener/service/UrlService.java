package com.urlshortener.service;

import com.urlshortener.domain.Url;
import com.urlshortener.repository.UrlRepository;

public class UrlService {

    private UrlRepository urlRepository = new UrlRepository();

    public String createShortUrl(String originalUrl) throws Exception {

        if (urlRepository.existsByOriginalUrl(originalUrl)) return null;

        String code = ShortCodeGenerator.generate();
        Url url = new Url(code, originalUrl);
        urlRepository.save(url);
        return code;
    }

    public String getOriginalUrl(String code) throws Exception {

        Url url = urlRepository.findByShortCode(code);

        if (url == null) {
            return null;
        }

        return url.getOriginalUrl();
    }
}