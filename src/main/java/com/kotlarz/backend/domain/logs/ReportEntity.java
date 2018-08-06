package com.kotlarz.backend.domain.logs;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity customer;

    @OneToMany(mappedBy = "report", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventEntity> events;
}
