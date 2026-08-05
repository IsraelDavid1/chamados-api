package com.company.servicedesk.services;

import com.company.servicedesk.dtos.ApproveRequestAndCreateCallDTO;
import com.company.servicedesk.dtos.CallRequestApprovalDTO;
import com.company.servicedesk.dtos.CreateCallRequestDTO;
import com.company.servicedesk.exceptions.CallRequestAlreadyChangedException;
import com.company.servicedesk.exceptions.CallRequestNotFoundException;
import com.company.servicedesk.exceptions.UserNotFoundException;
import com.company.servicedesk.exceptions.WrongCallRequestStateInputException;
import com.company.servicedesk.models.CallModel;
import com.company.servicedesk.models.CallRequestModel;
import com.company.servicedesk.models.CallRequestState;
import com.company.servicedesk.models.UserModel;
import com.company.servicedesk.repositories.CallRepository;
import com.company.servicedesk.repositories.CallRequestRepository;
import com.company.servicedesk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CallRequestService {
    private final CallRequestRepository callRequestRepository;
    private final UserRepository userRepository;
    private final CallRepository callRepository;

    private CallRequestModel findCallRequestById(UUID callId) {
        return  callRequestRepository.findById(callId)
                .orElseThrow(() -> new CallRequestNotFoundException("Pedido não encontrado"));
    }

    private UserModel findUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public CallRequestModel createRequest(UUID userId, CreateCallRequestDTO data) {
        CallRequestModel request = new CallRequestModel();
        UserModel user = findUserById(userId);

        request.setCreatedBy(user);
        request.setCreatedAt(data.createdAt());
        request.setDescription(data.description());
        request.setRequestState(CallRequestState.PENDING);

        return callRequestRepository.save(request);
    }

    @Transactional
    public void deleteRequest (UUID callRequestId) {
        CallRequestModel request = findCallRequestById(callRequestId);

        callRequestRepository.delete(request);
    }

    @Transactional
    public CallRequestModel callRequestApproval(UUID userId, CallRequestApprovalDTO data) {
        CallRequestModel request = findCallRequestById(data.requestId());

        if (request.getRequestState() != CallRequestState.PENDING) {
            throw new CallRequestAlreadyChangedException("Pedido já foi aprovado/negado!");
        }

        request.setRequestState(data.callRequestState());
        request.setApprovedBy(findUserById(userId));
        request.setApprovedAt(data.approvalTime());
        request.setObservation(data.observation());

        if (request.getRequestState() == CallRequestState.PENDING) {
            throw new WrongCallRequestStateInputException("Estado do pedido não foi mudado");
        }

        return callRequestRepository.save(request);
    }

    @Transactional
    public CallModel approveCallRequestAndCreateCall(UUID userId, ApproveRequestAndCreateCallDTO data) {
        UserModel user = findUserById(userId);
        CallRequestModel request = findCallRequestById(data.requestId());
        CallModel call = new CallModel();

        request.setRequestState(CallRequestState.APPROVED);
        request.setApprovedBy(user);
        request.setApprovedAt(data.approvalTime());
        request.setObservation(data.observation());
        callRequestRepository.save(request);

        call.setBeginDate(data.approvalTime());
        call.setAssignedTo(user);
        call.setAsset(data.assets());
        call.setAssetsType(data.assetsType());
        call.setDepartment(data.departments());
        call.setFirstAnalysis(request.getDescription());
        return callRepository.save(call);
    }

    public CallRequestModel getCallRequest(UUID callRequestId) { return findCallRequestById(callRequestId); }

    public List<CallRequestModel> getAllCallRequestByUser(UUID userId) { return callRequestRepository.findByUserId(userId); }

    public List<CallRequestModel> getAllCallRequest() { return callRequestRepository.findAll(); }
}
