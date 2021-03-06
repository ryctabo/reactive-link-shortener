package com.ryctabo.reactivelinkshortener.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ryctabo.reactivelinkshortener.repository.LinkRepository;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
class LinkServiceTest {

    private static final String DEFAULT_DOMAIN = "http://some-domain.com/";

    private final LinkRepository linkRepository = mock(LinkRepository.class);

    private final LinkService linkService = new LinkService(DEFAULT_DOMAIN, linkRepository);

    @BeforeEach
    void setUp() {
        when(linkRepository.save(any()))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
    }

    @Test
    void shortensLink() {
        StepVerifier.create(linkService.shortenLink("https://spring.io/"))
                .expectNextMatches(
                        result ->
                                Objects.nonNull(result)
                                        && result.length() > 0
                                        && result.startsWith(DEFAULT_DOMAIN))
                .expectComplete()
                .verify();
    }
}
