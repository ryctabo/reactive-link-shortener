package com.ryctabo.reactivelinkshortener.service;

import com.ryctabo.reactivelinkshortener.entity.Link;
import com.ryctabo.reactivelinkshortener.repository.LinkRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Service
public class LinkService {

    private final String baseUrl;
    private final LinkRepository linkRepository;

    public LinkService(@Value("${app.baseUrl}") String baseUrl, LinkRepository linkRepository) {
        this.baseUrl = baseUrl;
        this.linkRepository = linkRepository;
    }

    public Mono<String> shortenLink(String link) {
        String randomKey = RandomStringUtils.randomAlphabetic(6);
        return linkRepository
                .save(new Link(link, randomKey))
                .map(result -> baseUrl + result.getKey());
    }

    public Mono<String> getOriginalLink(String key) {
        return linkRepository.findByKey(key)
                .map(Link::getOriginalLink);
    }
}
