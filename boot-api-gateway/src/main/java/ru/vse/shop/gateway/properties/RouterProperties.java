package ru.vse.shop.gateway.properties;

import java.util.List;

public class RouterProperties {
    private List<String> endpoints;


    public List<String> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<String> endpoints) {
        this.endpoints = endpoints;
    }
}
