package com.ryctabo.reactivelinkshortener.repository;

import com.ryctabo.reactivelinkshortener.entity.Link;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Repository
public class RedisLinkRepository implements LinkRepository {

    private final ReactiveRedisOperations<String, String> redisOps;

    public RedisLinkRepository(ReactiveRedisOperations<String, String> redisOps) {
        this.redisOps = redisOps;
    }

    @Override
    public Mono<Link> save(Link link) {
        return redisOps.opsForValue().set(link.getKey(), link.getOriginalLink()).map(__ -> link);
    }

    @Override
    public Mono<Link> findByKey(String key) {
        return redisOps.opsForValue().get(key).map(result -> new Link(result, key));
    }
}
