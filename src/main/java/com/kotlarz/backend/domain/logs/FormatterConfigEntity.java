package com.kotlarz.backend.domain.logs;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormatterConfigEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String pattern;

    @Column(nullable = false)
    private Boolean fill;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "formatter")
    private CustomerEntity customer;
}
