package com.company.servicedesk.controllers;

import com.company.servicedesk.dtos.CallResponseDTO;
import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.models.UserModel;
import com.company.servicedesk.services.CallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/call")
public class CallController {
    private final CallService callService;

    @PostMapping
    public ResponseEntity<CallResponseDTO> createCall(@AuthenticationPrincipal UserModel user, @RequestBody @Valid CreateCallDTO data) {
        CallModel call = callService.createCall(user.getId(), data);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(call));
    }

    @PatchMapping("/{callId}")
    public ResponseEntity<CallResponseDTO> finishCall(@PathVariable UUID callId, @RequestBody @Valid FinishCallDTO data) {
        CallModel call = callService.finishCall(callId, data);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(call));
    }

    @PostMapping("/finishedcall")
    public ResponseEntity<CallResponseDTO> createFinishedCall(@AuthenticationPrincipal UserModel user, @RequestBody @Valid CreateCompleteCallDTO data) {
        CallModel call = callService.createFinishedCall(user.getId(), data);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(call));
    }

    @DeleteMapping("/{callId}")
    public ResponseEntity<Void> deleteCall(@PathVariable UUID callId) {
        callService.deleteCall(callId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public  ResponseEntity<List<CallResponseDTO>> getAllCalls(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getAllCalls().stream().map(this::toDTO).toList());
    }

    @GetMapping("/{callId}")
    public ResponseEntity<CallResponseDTO> getCall(@PathVariable UUID callId) {
        CallModel call = callService.getCall(callId);
        return ResponseEntity.status(HttpStatus.OK).body(toDTO(call));
    }

    @GetMapping("/mycalls")
    public ResponseEntity<List<CallResponseDTO>> getMyCalls(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getMyCalls(user.getId()).stream().map(this::toDTO).toList());
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public  ResponseEntity<List<CallResponseDTO>> getCallsByMonth(@AuthenticationPrincipal UserModel user, @RequestParam LocalDate beginDate, @RequestParam LocalDate lastDate) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getAssignedCallsByMonth(user.getId(), beginDate, lastDate).stream().map(this::toDTO).toList());
    }

    private CallResponseDTO toDTO(CallModel call) {
        return new CallResponseDTO(
                call.getId(),
                call.getBeginDate(),
                call.getAsset(),
                call.getAssetsType(),
                call.getDepartment(),
                call.getFirstAnalysis(),
                call.getSolution(),
                call.getEndDate(),
                call.getCallState(),
                call.getCreatedBy() != null ? call.getCreatedBy().getId() : null,
                call.getAssignedTo() != null ? call.getAssignedTo().getId() : null
        );
    }
}
