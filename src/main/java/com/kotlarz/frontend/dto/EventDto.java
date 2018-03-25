package com.kotlarz.frontend.dto;

import com.kotlarz.backend.domain.EventEntity;
import lombok.Data;

import java.util.Date;

@Data
public class EventDto {
    private Long id;

    private Date date;

    private String threadName;

    private String username;

    private String level;

    private String classname;

    private byte[] content;

    public EventDto(EventEntity domain) {
        date = domain.getDate();
        threadName = domain.getThreadName();
        username = domain.getUsername();
        level = domain.getLevel();
        classname = domain.getClassname();
        content = domain.getContent();
    }
}
