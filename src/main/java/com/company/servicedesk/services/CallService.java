package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.exceptions.AlreadyFinishedCallException;
import com.company.servicedesk.exceptions.CallNotFoundException;
import com.company.servicedesk.exceptions.UserNotFoundException;
import com.company.servicedesk.models.*;
import com.company.servicedesk.repositories.CallRepository;
import com.company.servicedesk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CallService {
    private final CallRepository callRepository;
    private final UserRepository userRepository;

    private CallModel buildBaseCall(LocalDate beginDate, UserModel tech,
                                    Assets asset, AssetsType assetType,
                                    String department, String firstAnalysis) {
        CallModel call = new CallModel();

        call.setBeginDate(beginDate);
        call.setAssignedTo(tech);
        call.setAsset(asset);
        call.setAssetsType(assetType);
        call.setDepartment(department);
        call.setFirstAnalysis(firstAnalysis);

        return call;
    }

    private UserModel findUserByLogin(String userLogin) {
        return userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    private UserModel findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    private CallModel findCallById(UUID callId) {
        return callRepository.findById(callId)
                .orElseThrow(() -> new CallNotFoundException("Chamado não encontrado"));
    }

    @Transactional
    public CallModel createCall(UUID userId, CreateCallDTO data) {
        UserModel tech = findUserByLogin(data.techLogin());
        CallModel call = buildBaseCall(data.beginDate(), tech, data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        UserModel user = findUserById(userId);

        call.setCreatedBy(user);

        return callRepository.save(call);
    }

    @Transactional
    public CallModel finishCall(UUID callId, FinishCallDTO data) {
        CallModel call = findCallById(callId);

        if (call.getCallState() == CallState.COMPLETE) {
            throw new AlreadyFinishedCallException("Chamado já finalizado!");
        }

        call.setSolution(data.solution());
        call.setEndDate(data.endDate());
        call.setCallState(CallState.COMPLETE);

        return callRepository.save(call);
    }

    @Transactional
    public CallModel createFinishedCall(UUID userId, CreateCompleteCallDTO data){
        UserModel tech = findUserByLogin(data.techLogin());
        CallModel call = buildBaseCall(data.beginDate(), tech, data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        UserModel user = findUserById(userId);

        call.setCreatedBy(user);
        call.setSolution(data.solution());
        call.setEndDate(data.endDate());
        call.setCallState(CallState.COMPLETE);

        return callRepository.save(call);
    }

    @Transactional
    public void deleteCall(UUID callId) {
        CallModel model = findCallById(callId);

        callRepository.delete(model);
    }

    public CallModel getCall(UUID callId) {
        return findCallById(callId);
    }

    public List<CallModel> getMyCalls(UUID userId) {
        return callRepository.findByUserId(userId);
    }

    public List<CallModel> getAssignedCallsByMonth(UUID techId, LocalDate beginDate, LocalDate lastDate) {
        return callRepository.findByMonth(techId ,beginDate, lastDate);
    }

    public List<CallModel> getAllCalls() {
        return callRepository.findAll();
    }
}
