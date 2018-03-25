package com.kotlarz.frontend.util;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class PathBuilder {
    private String viewName;

    private List<Object> parameters;

    private PathBuilder() {
        viewName = StringUtils.EMPTY;
        parameters = new LinkedList<>();
    }

    public static PathBuilder getInstance() {
        return new PathBuilder();
    }

    public PathBuilder view(Class<? extends View> viewClass) {
        boolean isView = viewClass.isAnnotationPresent(SpringView.class);
        if (isView) {
            SpringView viewAnnotation = viewClass.getAnnotation(SpringView.class);
            viewName = viewAnnotation.name();
        } else {
            throw new IllegalArgumentException(viewClass + " has no annotation " + SpringView.class);
        }

        return this;
    }

    public PathBuilder param(Object param) {
        parameters.add(param);
        return this;
    }

    public String build() {
        parameters.add(0, viewName);
        return ParametersUtil.createPath(parameters.toArray());
    }
}
