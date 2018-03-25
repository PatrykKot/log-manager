package com.kotlarz.frontend.util;

import com.kotlarz.backend.domain.FormatterConfigEntity;
import com.kotlarz.backend.formatter.FormatPhrase;
import com.kotlarz.frontend.dto.EventDto;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogFormatter {
    private String pattern;

    private Boolean fill;

    public LogFormatter(FormatterConfigEntity domain) {
        pattern = domain.getPattern();
        fill = domain.getFill();
    }

    public List<String> format(List<EventDto> events) {
        List<Map<FormatPhrase, String>> preparedEventValues = prepareEventValuesMap(events);

        if (fill) {
            fillMaximumLength(preparedEventValues);
        }

        return preparedEventValues.stream()
                .map(map -> format(map))
                .collect(Collectors.toList());
    }

    private List<Map<FormatPhrase, String>> prepareEventValuesMap(List<EventDto> events) {
        return events.stream()
                .map(event -> {
                    Map<FormatPhrase, String> formatMap = new HashMap<>();

                    for (FormatPhrase formatPhrase : FormatPhrase.values()) {
                        String value = formatPhrase.getFormatFunction().apply(event);
                        if (StringUtils.isNotEmpty(value)) {
                            formatMap.put(formatPhrase, value);
                        }
                    }

                    return formatMap;
                })
                .collect(Collectors.toList());
    }

    private void fillMaximumLength(List<Map<FormatPhrase, String>> preparedEventValues) {
        for (FormatPhrase formatPhrase : FormatPhrase.values()) {
            Optional<Integer> optionalMaxLength = preparedEventValues.stream()
                    .map(map -> map.get(formatPhrase))
                    .filter(value -> StringUtils.isNotEmpty(value))
                    .map(phrase -> phrase.length())
                    .max(Integer::compare);

            if (optionalMaxLength.isPresent()) {
                Integer maxLength = optionalMaxLength.get();
                preparedEventValues.forEach(map -> {
                    if (map.containsKey(formatPhrase)) {
                        String phrase = map.get(formatPhrase);
                        while (phrase.length() < maxLength) {
                            phrase += " ";
                        }

                        map.put(formatPhrase, phrase);
                    }
                });
            }
        }
    }

    private String format(Map<FormatPhrase, String> valuesMap) {
        String log = new String(pattern);

        for (FormatPhrase formatPhrase : FormatPhrase.values()) {
            String value = valuesMap.get(formatPhrase);
            if (StringUtils.isNotEmpty(value)) {
                String phrase = formatPhrase.getPhrase();
                log = log.replace(phrase, value);
            }
        }

        return log.trim();
    }
}
