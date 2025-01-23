package com.whatdo.global.config;

import java.util.Arrays;

public enum ClientConfig {
    APP,
    WEB;

    public static ClientConfig fromString(String value) {
        return Arrays.stream(ClientConfig.values())
            .filter(client ->
                client.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(value + " is not a valid ClientConfig"));
    }
}
