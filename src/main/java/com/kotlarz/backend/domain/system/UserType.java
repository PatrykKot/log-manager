package com.kotlarz.backend.domain.system;

import java.util.stream.Stream;

public enum UserType {
    ADMIN, STANDARD;

    public static String[] getAll() {
        return Stream.of(values())
                .map(item -> item.toString())
                .toArray(String[]::new);
    }
}
