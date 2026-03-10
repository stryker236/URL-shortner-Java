# URL Shortener – TODO

## Phase 1 – Database
- [x] PostgreSQL container running
- [x] Database connection working
- [x] Create urls table
- [ ] Verify insert query
- [ ] Verify lookup query
- [ ] Test index on short_code
- [ ] Run EXPLAIN ANALYZE on lookup query
- [ ] Insert large dataset (100k+ rows) and test lookup speed

## Phase 2 – Core Domain
- [ ] Create Url entity
- [ ] Create ShortCodeGenerator
- [ ] Generate large sample of codes (1M)
- [ ] Check collision rate
- [ ] Compare random vs sequential Base62 generation

## Phase 3 – Repository Layer
- [ ] Implement UrlRepository
- [ ] Implement save(shortCode, originalUrl)
- [ ] Implement findByShortCode(shortCode)
- [ ] Test repository with manual inserts
- [ ] Insert large dataset (100k–1M rows)
- [ ] Measure lookup latency

## Phase 4 – Service Layer
- [ ] Implement UrlService
- [ ] createShortUrl(originalUrl)
- [ ] getOriginalUrl(shortCode)
- [ ] Handle duplicate short codes with retry
- [ ] Validate URL format
- [ ] Write basic unit tests for service logic

## Phase 5 – API Layer
- [ ] Setup SparkJava server
- [ ] POST /urls endpoint
- [ ] GET /:shortCode redirect endpoint
- [ ] Return correct HTTP status codes
- [ ] Test endpoints with curl
- [ ] Test redirect behavior in browser

## Phase 6 – Basic Testing
- [ ] Test full flow (create → redirect)
- [ ] Verify records stored in database
- [ ] Simulate multiple requests
- [ ] Run small load test (~1k requests)
- [ ] Measure latency

## Phase 7 – Reliability
- [ ] Validate input URLs
- [ ] Return JSON responses
- [ ] Handle duplicate short codes
- [ ] Add error handling
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