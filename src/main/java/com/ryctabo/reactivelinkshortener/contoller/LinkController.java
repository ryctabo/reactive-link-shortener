package com.ryctabo.reactivelinkshortener.contoller;

import com.ryctabo.reactivelinkshortener.domain.CreateLinkRequest;
import com.ryctabo.reactivelinkshortener.domain.CreateLinkResponse;
import com.ryctabo.reactivelinkshortener.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@RestController
@RequiredArgsConstructor
public class LinkController {

    private final LinkService linkService;

    @PostMapping("/link")
    public Mono<CreateLinkResponse> create(@RequestBody CreateLinkRequest request) {
        return linkService.shortenLink(request.getLink()).map(CreateLinkResponse::new);
    }

    @GetMapping("/{key}")
    public Mono<ResponseEntity<Object>> getLink(@PathVariable String key) {
        return linkService
                .getOriginalLink(key)
                .map(
                        link ->
                                ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                                        .header("Location", link)
                                        .build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
