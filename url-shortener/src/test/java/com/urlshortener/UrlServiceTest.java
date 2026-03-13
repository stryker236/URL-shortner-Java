package com.urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.urlshortener.domain.Url;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.service.UrlService;


class UrlServiceTest {
    @Test
    void shouldCreateShortUrl() throws Exception {

        UrlRepository repo = mock(UrlRepository.class);

        when(repo.existsByOriginalUrl("https://google.com")).thenReturn(false);

        UrlService service = new UrlService(repo);

        String code = service.createShortUrl("https://google.com");

        assertNotNull(code);
        verify(repo).save(any(Url.class));
    }

    @Test
    void shouldReturnNullIfUrlExists() throws Exception {
        UrlRepository repo = mock(UrlRepository.class);

        when(repo.existsByOriginalUrl("https://google.com")).thenReturn(true);

        UrlService service = new UrlService(repo);

        String result = service.createShortUrl("https://google.com");

        assertNull(result);
    }

    @Test
    void shouldReturnOriginalUrl() throws Exception {
        UrlRepository repo = mock(UrlRepository.class);

        Url url = new Url("abc123", "https://google.com");

        when(repo.findByShortCode("abc123")).thenReturn(url);

        UrlService service = new UrlService(repo);

        String result = service.getOriginalUrl("abc123");

        assertEquals("https://google.com", result);
    }

    @Test
    void shouldReturnNullIfCodeNotFound() throws Exception {
        UrlRepository repo = mock(UrlRepository.class);

        when(repo.findByShortCode("abc123")).thenReturn(null);

        UrlService service = new UrlService(repo);

        String result = service.getOriginalUrl("abc123");

        assertNull(result);
    }
}
