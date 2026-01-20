package com.elearning.elearning_sdk.service;

import com.elearning.elearning_sdk.model.SaveRedisModel;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.JacksonHashMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
public class RedisService {

    private final ReactiveHashOperations<String, String, Object> hashOperations;
    private final ReactiveValueOperations<String, Object> valueOperations;
    private final HashMapper<Object, String, Object> mapper;

    public RedisService(
        ReactiveRedisTemplate<String, Object> template
    ) {
        this.hashOperations = template.opsForHash();
        this.valueOperations = template.opsForValue();
        this.mapper = JacksonHashMapper.flattening();
    }

    public Mono<Void> save(
        String key,
        String value
    ) {
        return Mono.defer(() -> {
            if (value == null) {
                return Mono.empty();
            }
            return valueOperations.set(key, value)
                .then();
        });
    }

    public Mono<Void> save(
        String key,
        SaveRedisModel model
    ) {
        return Mono.defer(() -> {
            Map<String, Object> mappedHash = mapper.toHash(model);
            if (Objects.nonNull(mappedHash)) {
                return hashOperations.putAll(key, mappedHash)
                    .then();
            }
            return Mono.empty();
        });
    }

    public Mono<Object> loadHash(String key) {
        return Mono.defer(() ->
            hashOperations.entries(key)
                .collectMap(Map.Entry::getKey, Map.Entry::getValue)
                .mapNotNull(mapper::fromHash)
        );
    }

    public Mono<Object> loadValue(String key) {
        return valueOperations.get(key);
    }

    public Mono<Void> removeValue(String key) {
        return valueOperations.delete(key)
            .then();
    }

    public Mono<Void> removeHash(String key) {
        return hashOperations.delete(key)
            .then();
    }
}
