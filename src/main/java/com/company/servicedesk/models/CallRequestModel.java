package com.company.servicedesk.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CallRequests") //plural to avoid errors with the postgres
@Table(name = "tb_call_request")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CallRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UserModel createdBy;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CallRequestState requestState;

    @Column
    private UserModel approvedBy;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private UserModel deniedBy;

    @Column
    private LocalDateTime deniedAt;

    @Column
    private String observation;
}
