# URL Shortener – TODO

# Extras 
- [x] Improve the database conection management
    - [x] Database for testing
- [x] Improve the verification of orignal url existance in the database
- [ ] Understand better indexing
- [ ] Test all the layers
    - [ ] Understand Mocking and stubbing
    - [ ] Repository
        - Test with k6 or jMeter or wrk
    - [x] Service
        - [x] Test with JUnit and Mockito
    - [ ] API
        - [ ] Test with RestAssured or similar

## Phase 1 – Database
- [x] PostgreSQL container running
- [x] Database connection working
- [x] Create urls table
- [x] Verify insert query
- [x] Insert large dataset (100k+ rows) and test lookup speed
- [x] Verify lookup query
- [x] Test index on short_code
- [x] Run EXPLAIN ANALYZE on lookup query

## Phase 2 – Core Domain
- [x] Create Url entity
- [x] Create ShortCodeGenerator
- [ ] Generate large sample of codes (1M)
- [ ] Check collision rate
- [ ] Compare random vs sequential Base62 generation

## Phase 3 – Repository Layer
- [x] Implement UrlRepository
- [x] Implement save(shortCode, originalUrl)
- [x] Implement findByShortCode(shortCode)
- [x] Test repository with manual inserts
- [x] Insert large dataset (100k–1M rows)
- [x] Measure lookup latency

## Phase 4 – Service Layer
- [x] Implement UrlService
- [x] createShortUrl(originalUrl)
- [x] getOriginalUrl(shortCode)
- [x] Handle duplicate short codes with retry
- [x] Validate URL format
    - [ ] Create a validator for URL format 
- [x] Write basic unit tests for service logic

## Phase 5 – API Layer
- [x] Setup SparkJava server
- [x] POST /urls endpoint
- [x] GET /urls/:shortCode redirect endpoint
- [x] GET /urls/:shortCode/redirect endpoint
- [x] Return correct HTTP status codes
- [x] Test endpoints with curl
- [x] Test redirect behavior in browser

## Phase 6 – Basic Testing
- [ ] Test full flow (create → redirect)
- [ ] Verify records stored in database
- [ ] Simulate multiple requests
- [ ] Run small load test (~1k requests)
- [ ] Measure latency

## Phase 7 – Reliability
- [x] Validate input URLs
- [ ] Return JSON responses
- [x] Handle duplicate short codes
- [x] Add error handling
- [ ] Handle database connection failures
- [ ] Add defensive checks

## Phase 8 – Caching
- [ ] Integrate Redis
- [ ] Cache redirect lookups
- [ ] Implement cache fallback to database
- [ ] Add TTL for cache entries
- [ ] Measure latency improvement
- [ ] Measure cache hit rate

## Phase 9 – Observability
- [ ] Add request logging
- [ ] Log errors
- [ ] Track request latency
- [ ] Add basic metrics (requests/sec)
- [ ] Monitor database query times

## Phase 10 – Load Testing
- [ ] Install load testing tool (wrk / k6 / hey)
- [ ] Run 1k req/sec test
- [ ] Run 5k req/sec test
- [ ] Run 10k req/sec test
- [ ] Identify bottlenecks (CPU / DB / Redis)

## Phase 11 – Scaling Concepts
- [ ] Run multiple API instances
- [ ] Add load balancer (NGINX or similar)
- [ ] Test distributed traffic
- [ ] Verify stateless behavior
- [ ] Measure performance with multiple instances

## Phase 12 – Advanced Scaling
- [ ] Research read replicas
- [ ] Research database sharding
- [ ] Research Redis cluster
- [ ] Research CDN edge caching

## Phase 13 – Cost Awareness
- [ ] Estimate infrastructure requirements
- [ ] Estimate cloud cost for small deployment
- [ ] Estimate cloud cost for scaled deployment
- [ ] Understand autoscaling strategies

## Phase 14 – Optional Advanced Features
- [ ] Implement Base62 ID encoding
- [ ] Add click analytics
- [ ] Add rate limiting
- [ ] Add custom short codes
- [ ] Add expiration for links
- [ ] Dockerize backend service