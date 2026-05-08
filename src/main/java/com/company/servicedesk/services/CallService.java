package com.company.servicedesk.services;

import com.company.servicedesk.dtos.CreateCallDTO;
import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.dtos.FinishCallDTO;
import com.company.servicedesk.exceptions.AlreadyFinishedCallException;
import com.company.servicedesk.exceptions.CallNotFoundException;
import com.company.servicedesk.exceptions.UserNotFoundException;
import com.company.servicedesk.exceptions.UserWithNoPrivilegeException;
import com.company.servicedesk.models.*;
import com.company.servicedesk.repositories.CallRepository;
import com.company.servicedesk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CallService {
    private final CallRepository callRepository;
    private final UserRepository userRepository;

    private CallModel buildBaseCall(LocalDate beginDate, String techLogin, Assets asset,
                                    AssetsType assetType, String department,
                                    String firstAnalysis) {
        CallModel call = new CallModel();
        UserModel tech = userRepository.findByLogin(techLogin)
                        .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        call.setBeginDate(beginDate);
        call.setAssignedTo(tech);
        call.setAsset(asset);
        call.setAssetsType(assetType);
        call.setDepartment(department);
        call.setFirstAnalysis(firstAnalysis);

        return call;
    }

    private UserModel assertHasElevatedRole(UUID userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        //used EnumSet in this version for scalability
        if (!EnumSet.of(UserRole.TECH, UserRole.ADMIN).contains(user.getRole())) {
            throw new UserWithNoPrivilegeException("Usuário sem privilégio");
        }
        return user;
    }

    private UserModel findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    private CallModel findCallById(UUID callId) {
        return callRepository.findById(callId)
                .orElseThrow(() -> new CallNotFoundException("Chamado não encontrado"));
    }

    // criar check de user logado, e ResponseDTO
    @Transactional
    public CallModel createCall(UUID userId, CreateCallDTO data) {
        CallModel call = buildBaseCall(data.beginDate(), data.techLogin(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        UserModel user = findUserById(userId);

        call.setCreatedBy(user);

        return callRepository.save(call);
    }

    // criar check de user logado
    @Transactional
    public CallModel finishCall(UUID callId, FinishCallDTO data) {
        CallModel call = findCallById(callId);

        // create exception
        if (call.getCallState() == CallState.COMPLETE) {
            throw new AlreadyFinishedCallException("Chamado já finalizado!");
        }

        call.setSolution(data.solution());
        call.setEndDate(data.endDate());
        call.setCallState(CallState.COMPLETE);

        return callRepository.save(call);
    }

    // criar check de user logado
    @Transactional
    public CallModel createFinishedCall(UUID userId, CreateCompleteCallDTO data){
        CallModel call = buildBaseCall(data.beginDate(), data.techLogin(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis());

        UserModel user = findUserById(userId);

        call.setCreatedBy(user);
        call.setSolution(data.solution());
        call.setEndDate(data.endDate());
        call.setCallState(CallState.COMPLETE);

        return callRepository.save(call);
    }

    // criar check de user logado
    @Transactional
    public void deleteCall(UUID callId) {
        CallModel model = findCallById(callId);

        callRepository.delete(model);
    }

    // criar check de user logado
    public CallModel getCall(UUID callId) {
        return findCallById(callId);
    }

    // criar check de user logado
    public List<CallModel> getMyCalls(UUID userId) {
        return callRepository.findByUserId(userId);
    }

    //check userId not implemented
    public List<CallModel> getAssignedCallsByMonth(UUID techId, LocalDate beginDate, LocalDate lastDate) {
        UserModel user = assertHasElevatedRole(techId);
        return callRepository.findByMonth(user.getId() ,beginDate, lastDate);
    }

    public List<CallModel> getAllCalls(UUID userId) {
        assertHasElevatedRole(userId);
        return callRepository.findAll();
    }
}
