package com.company.servicedesk.controllers;

import com.company.servicedesk.dtos.*;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.models.CallRequestModel;
import com.company.servicedesk.models.UserModel;
import com.company.servicedesk.services.CallRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/request")
public class CallRequestController {
    private final CallRequestService callRequestService;

    private CallRequestResponseDTO toDTO(CallRequestModel request) {
        return new CallRequestResponseDTO(
                request.getId(),
                request.getCreatedAt(),
                request.getDescription(),
                request.getRequestState()
        );
    }

    private CallResponseDTO toDTO(CallModel call) {
        return new CallResponseDTO(
                call.getId(),
                call.getBeginDate(),
                call.getAsset(),
                call.getAssetsType(),
                call.getDepartment(),
                call.getFirstAnalysis(),
                call.getSolution() != null ? call.getSolution() : null,
                call.getEndDate() != null ? call.getEndDate() : null,
                call.getCallState(),
                call.getCreatedBy() != null ? call.getCreatedBy().getId() : null,
                call.getAssignedTo() != null ? call.getAssignedTo().getId() : null
        );
    }

    @PostMapping
    public ResponseEntity<CallRequestResponseDTO> createRequest(@AuthenticationPrincipal UserModel user, CreateCallRequestDTO data) {
        CallRequestModel request = callRequestService.createRequest(user.getId(), data);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(request));
    }

    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public ResponseEntity<Void> deleteRequest(@PathVariable UUID requestId) {
        callRequestService.deleteRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/approval")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public ResponseEntity<CallRequestResponseDTO> callRequestApproval(@AuthenticationPrincipal UserModel user, CallRequestApprovalDTO data) {
        CallRequestModel request = callRequestService.callRequestApproval(user.getId(), data);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(toDTO(request));
    }

    @PostMapping("/approve-and")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public ResponseEntity<CallResponseDTO> approveCallRequestAndCreateCall(@AuthenticationPrincipal UserModel user, ApproveRequestAndCreateCallDTO data) {
        CallModel call = callRequestService.approveCallRequestAndCreateCall(user.getId(), data);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(call));
    }

    @GetMapping("/user")
    public ResponseEntity<List<CallRequestResponseDTO>> getAllCallRequestByUser(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callRequestService.getAllCallRequestByUser(user.getId()).stream().map(this::toDTO).toList());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<CallRequestResponseDTO> getCallRequest(@PathVariable UUID requestId) {
        CallRequestModel request = callRequestService.getCallRequest(requestId);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public ResponseEntity<List<CallRequestResponseDTO>> getAllCallRequest(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callRequestService.getAllCallRequest().stream().map(this::toDTO).toList());
    }
}
