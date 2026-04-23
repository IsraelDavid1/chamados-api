package com.company.servicedesk.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Calls")
@Table(name = "tb_calls")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CallModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private LocalDate beginDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Assets asset;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetsType assetsType;

    @Column(nullable = false)
    private String department;

    @Column
    private String firstAnalysis;

    @Column
    private String solution;

    @Column LocalDate endDate;
}
