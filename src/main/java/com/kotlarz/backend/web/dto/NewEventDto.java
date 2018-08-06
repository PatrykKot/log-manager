package com.kotlarz.backend.web.dto;

import com.kotlarz.backend.domain.logs.EventEntity;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Data
public class NewEventDto {
    private Date date;

    private String thread;

    private String username;

    private String level;

    private String classname;

    private String content;

    public EventEntity toEntity() {
        return EventEntity.builder()
                .date(date)
                .threadName(thread)
                .username(username)
                .level(level)
                .classname(classname)
                .content(content.getBytes(StandardCharsets.UTF_8))
                .build();
    }
}
