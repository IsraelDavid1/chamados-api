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
    WHERE c.user.id = :userID
    AND c.beginDate BETWEEN
    :beginDate AND :lastDate
    """)
    List<CallModel> findByMouth(@Param("userId") UUID userId,
                                @Param("beginDate") LocalDate beginDate,
                                @Param("lastDate") LocalDate lastDate);
}
