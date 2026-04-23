package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.repositories.CallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CallService {
    private final CallRepository callRepository;

    // criar check de user logado
    public CallModel CreateCall(CreateCallDTO data) {
        CallModel call = new CallModel();
        call.setBeginDate(data.beginDate());
        call.setAsset(data.asset());
        call.setAssetsType(data.assetType());
        call.setDepartment(data.department());
        call.setFirstAnalysis(data.firstAnalysis());

        return callRepository.save(call);
    }
}
