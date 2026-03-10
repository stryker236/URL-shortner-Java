# URL Shortener – TODO

## Phase 1 – Database
[✓] PostgreSQL container running
[✓] Database connection working
[✓] Create urls table
[ ] Verify insert query
[ ] Verify lookup query

## Phase 2 – Core Domain
[ ] Create Url entity
[ ] Create ShortCodeGenerator

## Phase 3 – Repository Layer
[ ] Implement UrlRepository
[ ] Implement save(shortCode, originalUrl)
[ ] Implement findByShortCode(shortCode)
[ ] Test repository with manual inserts

## Phase 4 – Service Layer
[ ] Implement UrlService
[ ] createShortUrl(originalUrl)
[ ] getOriginalUrl(shortCode)

## Phase 5 – API Layer
[ ] Setup SparkJava server
[ ] POST /urls endpoint
[ ] GET /:shortCode redirect endpoint

## Phase 6 – Basic Testing
[ ] Test URL creation with curl
[ ] Test redirect in browser
[ ] Verify records stored in database

## Phase 7 – Improvements
[ ] Validate input URLs
[ ] Return JSON responses
[ ] Handle duplicate short codes
[ ] Add error handling

## Phase 8 – Performance
[ ] Integrate Redis
[ ] Cache redirect lookups
[ ] Add cache invalidation

## Phase 9 – Production Readiness
[ ] Logging
[ ] Rate limiting
[ ] Metrics
[ ] Dockerize backend