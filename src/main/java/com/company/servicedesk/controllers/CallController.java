package com.company.servicedesk.controllers;

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
    public ResponseEntity<CallModel> createCall(@AuthenticationPrincipal UserModel user, @RequestBody @Valid CreateCallDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callService.createCall(user.getId(), data));
    }

    @PatchMapping("/{callId}")
    public ResponseEntity<CallModel> finishCall(@PathVariable UUID callId, @RequestBody @Valid FinishCallDTO data) {
        return ResponseEntity.ok(callService.finishCall(callId, data));
    }

    @PostMapping("/finishedcall")
    public ResponseEntity<CallModel> createFinishedCall(@AuthenticationPrincipal UserModel user, @RequestBody @Valid CreateCompleteCallDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callService.createFinishedCall(user.getId(), data));
    }

    @DeleteMapping("/{callId}")
    public ResponseEntity<Void> deleteCall(@PathVariable UUID callId) {
        callService.deleteCall(callId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public  ResponseEntity<List<CallModel>> getAllCalls(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getAllCalls(user.getId()));
    }

    @GetMapping("/{callId}")
    public ResponseEntity<CallModel> getCall(@PathVariable UUID callId) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getCall(callId));
    }

    @GetMapping("/mycalls")
    public ResponseEntity<List<CallModel>> getMyCalls(@AuthenticationPrincipal UserModel user) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getMyCalls(user.getId()));
    }

    @GetMapping("/monthly")
    @PreAuthorize("hasAnyRole('TECH', 'ADMIN')")
    public  ResponseEntity<List<CallModel>> getCallsByMonth(@AuthenticationPrincipal UserModel user, @RequestParam LocalDate beginDate, @RequestParam LocalDate lastDate) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getAssignedCallsByMonth(user.getId(), beginDate, lastDate));
    }
}
