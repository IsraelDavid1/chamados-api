package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.dtos.MonthDTO;
import com.company.servicedesk.exceptions.AlreadyFinishedCallException;
import com.company.servicedesk.exceptions.CallNotFoundException;
import com.company.servicedesk.models.Assets;
import com.company.servicedesk.models.AssetsType;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.models.CallState;
import com.company.servicedesk.repositories.CallRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CallService {
    private final CallRepository callRepository;

    private CallModel buildBaseCall(LocalDate beginDate, Assets asset,
                                    AssetsType assetType, String department,
                                    String firstAnalysis) {
        CallModel call = new CallModel();

        call.setBeginDate(beginDate);
        call.setAsset(asset);
        call.setAssetsType(assetType);
        call.setDepartment(department);
        call.setFirstAnalysis(firstAnalysis);

        return call;
    }
    // criar check de user logado, e ResponseDTO
    @Transactional
    public CallModel createCall(CreateCallDTO data) {
        CallModel call = buildBaseCall(data.beginDate(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        return callRepository.save(call);
    }

    // criar check de user logado
    @Transactional
    public CallModel finishCall(UUID callID, FinishCallDTO data) {
        CallModel call = callRepository.findById(callID)
                .orElseThrow(() -> new CallNotFoundException("Chamado não encontrado!"));

        // create exception
        if (call.getCallState() == CallState.COMPLETE) {
            throw new AlreadyFinishedCallException("Chamado já finalizado!");
        }

        call.setSolution(data.solution());
        call.setEndDate(data.endDate());

        return callRepository.save(call);
    }

    // criar check de user logado
    @Transactional
    public CallModel createFinishedCall(CreateCompleteCallDTO data){
        CallModel call = buildBaseCall(data.beginDate(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        call.setSolution(data.solution());
        call.setEndDate(data.endDate());
        call.setCallState(CallState.COMPLETE);

        return callRepository.save(call);
    }

    // criar check de user logado
    public void deleteCall(UUID callId) {
        @NonNull
        CallModel model = callRepository.findById(callId)
                .orElseThrow(() -> new CallNotFoundException("Chamado não encontrado"));

        callRepository.delete(model);
    }

    // criar check de user logado
    public CallModel getCall(UUID callId) {
        @NonNull
        CallModel call = callRepository.findById(callId)
                .orElseThrow(() -> new CallNotFoundException("Chamado não encontrado"));

        return call;
    }

    //check userId not implemented
    public List<CallModel> getCallsByMonth(UUID userId, MonthDTO data) {
        return callRepository.findByMouth(userId, data.beginDate(), data.lastDate());
    }

    public List<CallModel> getAllCalls(UUID userId) {
        return callRepository.findAll();
    }
}
