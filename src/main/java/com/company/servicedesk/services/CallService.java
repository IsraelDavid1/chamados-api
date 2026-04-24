package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.repositories.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CallService {
    private final CallRepository callRepository;

    // criar check de user logado
    public CallModel createCall(CreateCallDTO data) {
        CallModel call = new CallModel();

        call.setBeginDate(data.beginDate());
        call.setAsset(data.asset());
        call.setAssetsType(data.assetType());
        call.setDepartment(data.department());
        call.setFirstAnalysis(data.firstAnalysis());

        return callRepository.save(call);
    }

    // criar check de user logado
    public CallModel finishCall(UUID callID, FinishCallDTO data) {
        CallModel call = callRepository.findById(callID)
                .orElseThrow(() -> new RuntimeException("Chamada não encontrada"));

        call.setSolution(data.solution());
        call.setEndDate(data.endDate());

        return callRepository.save(call);
    }

    // criar check de user logado e novo erro
    public CallModel createFinishedCall(CreateCompleteCallDTO data){
        CallModel call = new CallModel();

        call.setBeginDate(data.beginDate());
        call.setAsset(data.asset());
        call.setAssetsType(data.assetType());
        call.setDepartment(data.department());
        call.setFirstAnalysis(data.firstAnalysis());
        call.setSolution(data.solution());
        call.setEndDate(data.endDate());

        return callRepository.save(call);
    }

    // criar check de user logado, e novo erro
    public void deleteCall(UUID callId) {
        CallModel model = callRepository.findById(callId)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
    }
}
