package com.ryctabo.reactivelinkshortener.repository;

import com.ryctabo.reactivelinkshortener.entity.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@SpringBootTest
class RedisLinkRepositoryTest {

    @Autowired private RedisLinkRepository redisLinkRepository;

    private static final String ORIGINAL_LINK = "https://spring.io/";
    private static final String DEFAULT_KEY = "ryctabo";

    @Test
    void returnsSameLinkAsArgument() {
        Link link = new Link(ORIGINAL_LINK, DEFAULT_KEY);
        StepVerifier.create(redisLinkRepository.save(link)).expectNext(link).verifyComplete();
    }

    @Test
    void savesInRedis() {
        Link link = new Link(ORIGINAL_LINK, DEFAULT_KEY);
        StepVerifier.create(
                        redisLinkRepository
                                .save(link)
                                .flatMap(__ -> redisLinkRepository.findByKey(link.getKey())))
                .expectNext(link)
                .verifyComplete();
    }
}
