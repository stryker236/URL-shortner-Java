-- Active: 1773105009396@@host.docker.internal@5432@urlshortener_test
CREATE DATABASE urlshortener_test;
CREATE TABLE urls (
    id BIGSERIAL PRIMARY KEY,
    short_code VARCHAR(10) UNIQUE NOT NULL,
    original_url TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
SELECT current_database();
SELECT * FROM urls;
SELECT count(*) FROM urls;
TRUNCATE TABLE urls;

-- This is being used to prove that index is being used 
EXPLAIN ANALYZE
SELECT * FROM urls
WHERE short_code = 'abc123';

-- Check what is being done here later
INSERT INTO urls (short_code, original_url)
SELECT
    substring(md5(random()::text), 1, 10),
    'https://example.com'
FROM generate_series(1, 100000);