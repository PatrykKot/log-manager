package com.kotlarz.backend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTokenEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity customer;
}
