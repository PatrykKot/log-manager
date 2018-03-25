package com.kotlarz.frontend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventFilter {
    private String thread;

    private String user;

    private String level;

    private String classname;
}
