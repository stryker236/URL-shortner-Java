package com.urlshortener.service;

import com.urlshortener.domain.Url;
import com.urlshortener.repository.RedisRepository;
import com.urlshortener.repository.UrlRepository;
public class UrlService {

    private UrlRepository urlRepository;
    private RedisRepository redisRepository;

    public UrlService(UrlRepository repo, RedisRepository redis) throws Exception {
        this.urlRepository = repo;
        this.redisRepository = redis;
    }

    public String createShortUrl(String originalUrl) throws Exception {
        String cachedCode = redisRepository.get(originalUrl);
        if (cachedCode != null) {
            return null;
        }

        Url url = urlRepository.findByOriginalUrl(originalUrl);
        if (url != null) {
            redisRepository.save(originalUrl, url.getCode());
            return null;
        }

        String code = ShortCodeGenerator.generate();
        while (urlRepository.existsByShortCode(code)) {
            // In the unlikely event of a collision, generate a new code
            code = ShortCodeGenerator.generate();
        }
        Url newUrl = new Url(code, originalUrl);
        urlRepository.save(newUrl);
        redisRepository.save(originalUrl, code);
        return code;
    }

    public String getOriginalUrl(String code) throws Exception {
        String cachedOriginalUrl = redisRepository.get(code);
        if (cachedOriginalUrl != null) {
            return cachedOriginalUrl;
        }

        Url url = urlRepository.findByShortCode(code);
        if(url != null) {
            redisRepository.save(code, url.getUrl());
            return url.getUrl();
        }

        return null;

    }

    public void deleteShortUrl(String code) throws Exception {
        urlRepository.deleteByShortCode(code);
        redisRepository.delete(code);
    }

    public void deleteByOriginalUrl(String originalUrl) throws Exception {
        urlRepository.deleteByOriginalUrl(originalUrl);
        redisRepository.delete(originalUrl);
    }
}