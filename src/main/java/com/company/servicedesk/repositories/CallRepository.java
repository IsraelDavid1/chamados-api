package com.company.servicedesk.repositories;

import com.company.servicedesk.dtos.CallReportDTO;
import com.company.servicedesk.models.CallModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CallRepository extends JpaRepository<CallModel, UUID> {
    @Query("""
    SELECT call
    FROM Calls call
    WHERE call.assignedTo.id = :techId
    AND call.beginDate BETWEEN :beginDate AND :lastDate
    """)
    List<CallModel> findByMonth(@Param("techId") UUID techId,
                                @Param("beginDate") LocalDateTime beginDate,
                                @Param("lastDate") LocalDateTime lastDate);

    @Query("""
    SELECT call
    FROM Calls call
    WHERE call.createdBy.id = :userId
    """)
    List<CallModel> findByUserId(@Param("userId") UUID userId);

    @Query("""
    SELECT new com.company.servicedesk.dtos.CallReportDTO(
        call.beginDate,
        call.createdBy,
        call.assignedTo,
        call.asset,
        call.assetType,
        call.department,
        call.firstAnalysis,
        call.solution,
        call.endDate,
        call.urgency,
        call.impact
        ) FROM Calls call
    """)
    List<CallReportDTO> findCallsForReport();

    @Query("""
    SELECT new com.company.servicedesk.dtos.CallReportDTO(
        call.beginDate,
        call.createdBy,
        call.assignedTo,
        call.asset,
        call.assetType,
        call.department,
        call.firstAnalysis,
        call.solution,
        call.endDate,
        call.urgency,
        call.impact
        ) FROM Calls call
    WHERE call.beginDate BETWEEN
    :firstDayOfMonth AND :lastDayOfMonth
    """)
    List<CallReportDTO> findMonthlyCallsForReport(@Param("firstDayOfMonth") LocalDateTime firstDayOfMonth,
                                                  @Param("lastDayOfMonth") LocalDateTime lastDayOfMonth);
}
