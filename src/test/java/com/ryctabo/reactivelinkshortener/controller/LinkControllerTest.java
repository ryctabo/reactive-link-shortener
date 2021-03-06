package com.ryctabo.reactivelinkshortener.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.ryctabo.reactivelinkshortener.contoller.LinkController;
import com.ryctabo.reactivelinkshortener.service.LinkService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@WebFluxTest(controllers = LinkController.class)
class LinkControllerTest {

    @Autowired private WebTestClient webTestClient;

    @MockBean private LinkService linkService;

    private static final String ORIGINAL_LINK = "https://spring.io/";

    @Test
    void shortensLink() {
        when(linkService.shortenLink(ORIGINAL_LINK))
                .thenReturn(Mono.just("http://localhost:8080/ryctabo"));
        webTestClient
                .post()
                .uri("/link")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{\"link\":\"https://spring.io/\"}")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.shortenedLink")
                .value(val -> assertThat(val).isEqualTo("http://localhost:8080/ryctabo"));
    }

    @Test
    void redirectsToOriginalLink() {
        when(linkService.getOriginalLink(anyString())).thenReturn(Mono.just(ORIGINAL_LINK));
        webTestClient
                .get()
                .uri("/ryctabo")
                .exchange()
                .expectStatus()
                .isPermanentRedirect()
                .expectHeader()
                .value("Location", location -> assertThat(location).isEqualTo(ORIGINAL_LINK));
    }
}
