package com.kotlarz.configuration.security.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.vaadin.spring.annotation.SpringComponent;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringComponent
public class TokenRepository {
    public static final Long TOKEN_VALID_TIME_SEC = 7L * 24 * 3600;

    private Cache<String, Long> tokensCache = CacheBuilder.newBuilder()
            .expireAfterWrite(TOKEN_VALID_TIME_SEC, TimeUnit.SECONDS)
            .build();

    public String store(Long userId) {
        String token = UUID.randomUUID().toString();
        tokensCache.put(token, userId);
        return token;
    }

    public Optional<Long> get(String token) {
        return Optional.ofNullable(tokensCache.getIfPresent(token));
    }

    public void invalidate(Long userId) {
        List<String> tokensToRemove = tokensCache
                .asMap()
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(userId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        tokensCache.invalidateAll(tokensToRemove);
    }
}
