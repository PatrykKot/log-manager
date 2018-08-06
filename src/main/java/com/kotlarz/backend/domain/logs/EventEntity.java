package com.kotlarz.backend.domain.logs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    private String threadName;

    private String username;

    private String level;

    private String classname;

    @Lob
    private byte[] content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ReportEntity report;
}
