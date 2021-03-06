package com.ryctabo.reactivelinkshortener.repository;

import com.ryctabo.reactivelinkshortener.entity.Link;
import reactor.core.publisher.Mono;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
public interface LinkRepository {

    Mono<Link> save(Link link);

    Mono<Link> findByKey(String key);
}
