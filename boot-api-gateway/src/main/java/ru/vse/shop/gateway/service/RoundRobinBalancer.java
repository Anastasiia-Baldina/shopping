package ru.vse.shop.gateway.service;

import ru.vse.shop.gateway.properties.RouterProperties;
import ru.vse.shop.utils.Asserts;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinBalancer implements EndpointBalancer {
    private final AtomicInteger callCounter = new AtomicInteger();
    private final List<String> endpoints;

    public RoundRobinBalancer(RouterProperties routerProperties) {
        this.endpoints = Asserts.notEmpty(routerProperties.getEndpoints(), "endpoints");
    }

    @Override
    public String nextEndpoint() {
        int callCount = callCounter.incrementAndGet();
        return endpoints.get(Math.abs(callCount) % endpoints.size());
    }
}
