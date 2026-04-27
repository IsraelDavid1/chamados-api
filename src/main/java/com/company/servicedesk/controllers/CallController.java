package com.company.servicedesk.controllers;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.services.CallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/call")
public class CallController {
    private final CallService callService;

    @PostMapping
    public ResponseEntity<CallModel> createCall(@RequestBody @Valid CreateCallDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callService.createCall(data));
    }

    @PatchMapping
    public ResponseEntity<CallModel> finishCall(@PathVariable UUID calId, @RequestBody @Valid FinishCallDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callService.finishCall(calId, data));
    }

    @PostMapping("/finishedcall")
    public ResponseEntity<CallModel> createFinishedCall(@RequestBody @Valid CreateCompleteCallDTO data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(callService.createFinishedCall(data));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCall(@PathVariable UUID callId) {
        callService.deleteCall(callId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public  ResponseEntity<List<CallModel>> getAllCalls(UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(callService.getAllCalls(userId));
    }

    @GetMapping("/{callId}")
    public ResponseEntity<CallModel> getCall()
}
