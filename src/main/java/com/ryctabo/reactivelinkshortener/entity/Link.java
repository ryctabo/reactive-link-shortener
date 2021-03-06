package com.ryctabo.reactivelinkshortener.entity;

import lombok.Value;

/**
 * @author Gustavo Pacheco (ryctabo at gmail.com)
 * @version 1.0-SNAPSHOT
 */
@Value
public class Link {

    String originalLink;

    String key;
}
