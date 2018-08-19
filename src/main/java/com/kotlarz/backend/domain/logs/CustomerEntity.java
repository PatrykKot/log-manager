package com.kotlarz.backend.domain.logs;

import com.kotlarz.backend.domain.system.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long clearLogsAfterDays;

    @JoinColumn(name = "formatterid", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private FormatterConfigEntity formatter;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportEntity> reports;

    @JoinColumn(name = "tokenid", nullable = false)
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private CustomerTokenEntity customerToken;

    @ManyToMany(mappedBy = "availableCustomers")
    private List<UserEntity> permittedUsers;
}
