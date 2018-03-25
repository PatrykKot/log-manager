package com.kotlarz.backend.formatter;

import com.kotlarz.frontend.dto.EventDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Getter
@AllArgsConstructor
public enum FormatPhrase {
    DATE("{date}", event -> event.getDate().toString()),
    THREAD("{thread}", event -> event.getThreadName()),
    USER("{user}", event -> event.getUsername()),
    LEVEL("{level}", event -> event.getLevel()),
    CLASSNAME("{classname}", event -> event.getClassname()),
    CONTENT("{content}", event -> new String(event.getContent(), StandardCharsets.UTF_8));

    private String phrase;

    private Function<EventDto, String> formatFunction;
}
