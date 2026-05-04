package com.company.servicedesk.repositories;

import com.company.servicedesk.models.CallModel;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CallRepository extends JpaRepository<CallModel, UUID> {
    @Query("""
    SELECT c
    FROM Calls c
    WHERE c.beginDate BETWEEN
    :beginDate AND :lastDate
    """)
    List<CallModel> findByMouth(@Param("beginDate") LocalDate beginDate,
                                @Param("lastDate") LocalDate lastDate);

    @Query("""
    SElECT c
    FROM Calls c
    WHERE c.created_by.id = :userId
    """)
    List<CallModel> findByUser(@Param("userId") UUID userId);
}
