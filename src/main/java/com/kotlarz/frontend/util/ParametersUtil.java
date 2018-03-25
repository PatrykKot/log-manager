package com.kotlarz.frontend.util;

import com.vaadin.navigator.ViewChangeListener;
import org.apache.commons.lang3.StringUtils;

import javax.swing.plaf.SeparatorUI;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParametersUtil {
    public static final String SEPARATOR = "/";

    public static List<String> resolve(ViewChangeListener.ViewChangeEvent event) {
        String[] parameters = event.getParameters().split(SEPARATOR);
        return Stream.of(parameters)
                .map(parameter -> {
                    try {
                        return URLDecoder.decode(parameter, StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .filter(parameter -> StringUtils.isNotEmpty(parameter.trim()))
                .collect(Collectors.toList());
    }

    public static String createPath(Object... parameters) {
        return Stream.of(parameters)
                .map(parameter -> parameter.toString())
                .map(parameter -> {
                    try {
                        return URLEncoder.encode(parameter, StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.joining(SEPARATOR));
    }
}
