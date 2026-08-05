package com.company.servicedesk.repositories;

import com.company.servicedesk.models.CallRequestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CallRequestRepository extends JpaRepository<CallRequestModel, UUID> {
    @Query("""
    SELECT c
    FROM CallRequests c
    WHERE c.createdBy.id = :userId
    """)
    List<CallRequestModel> findByUserId(@Param("userId") UUID userId);
}
