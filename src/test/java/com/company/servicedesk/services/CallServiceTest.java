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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Mock
    private UserRepository userRepository;
    private CallService callService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        callService = new CallService(callRepository, userRepository);
    }

    @Test
    @DisplayName("Should create a basic call")
    void createCallSuccess() {
        UUID userId = UUID.randomUUID();
        CreateCallDTO data = createCallDTO();
        UserModel user = createUser();
        UserModel tech = createTech();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByLogin("tech01")).thenReturn(Optional.of(tech));
        when(callRepository.save(any(CallModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CallModel result = callService.createCall(userId, data);

        assertThat(result.getCreatedBy()).isEqualTo(user);
        assertThat(result.getAssignedTo()).isEqualTo(tech);
        assertThat(result.getCallState()).isEqualTo(CallState.INCOMPLETE);
        verify(callRepository, times(1)).save(any(CallModel.class));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException when user not found")
    void createCallFailure() {
        UUID userId = UUID.randomUUID();
        CreateCallDTO data = createCallDTO();

        when(userRepository.findByLogin("tech01")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> callService.createCall(userId, data))
                .isInstanceOf(UserNotFoundException.class);

        when(userRepository.findByLogin("tech01")).thenReturn(Optional.of(createTech()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> callService.createCall(userId, data))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Should finish a basic call")
    void finishCallSuccess() {
        UUID callId = UUID.randomUUID();
        LocalDateTime endDate = LocalDateTime.now();
        CallModel call = createCall();
        FinishCallDTO data = new FinishCallDTO("solved", endDate);

        when(callRepository.findById(callId)).thenReturn(Optional.of(call));
        when(callRepository.save(any(CallModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CallModel result = callService.finishCall(callId, data);

        assertThat(result.getSolution()).isEqualTo("solved");
        assertThat(result.getEndDate()).isEqualTo(endDate);
        assertThat(result.getCallState()).isEqualTo(CallState.COMPLETE);
        verify(callRepository, times(1)).save(any(CallModel.class));
    }

    @Test
    @DisplayName("Should throw AlreadyFinishedCallException when call already finished")
    void finishCallFailure() {
        UUID callId = UUID.randomUUID();
        LocalDateTime endDate = LocalDateTime.now();
        CallModel call = createCall();
        FinishCallDTO data = new FinishCallDTO("solved", endDate);

        call.setCallState(CallState.COMPLETE);

        when(callRepository.findById(callId)).thenReturn(Optional.of(call));

        assertThatThrownBy(() -> callService.finishCall(callId, data))
                .isInstanceOf(AlreadyFinishedCallException.class);
    }

    @Test
    @DisplayName("Should create a complete call")
    void createFinishedCallSuccess() {
        UUID userId = UUID.randomUUID();
        CreateCallDTO data = createCallDTO();
        String solution = "solved";
        LocalDateTime endDate = LocalDateTime.now();
        UserModel user = createUser();
        UserModel tech = createTech();
        CreateCompleteCallDTO completeData = new CreateCompleteCallDTO(data.beginDate(), data.techLogin(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis(), solution, endDate);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByLogin("tech01")).thenReturn(Optional.of(tech));
        when(callRepository.save(any(CallModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CallModel result = callService.createFinishedCall(userId, completeData);

        assertThat(result.getCreatedBy()).isEqualTo(user);
        assertThat(result.getAsset()).isEqualTo(data.asset());
        assertThat(result.getSolution()).isEqualTo("solved");
        assertThat(result.getEndDate()).isEqualTo(endDate);
        assertThat(result.getCallState()).isEqualTo(CallState.COMPLETE);
        verify(callRepository, times(1)).save(any(CallModel.class));
    }

    @Test
    @DisplayName("Should throw UserNotFoundException if user or tech is missing")
    void createFinishedCallFailure() {
        UUID userId = UUID.randomUUID();
        CreateCallDTO data = createCallDTO();
        String solution = "solved";
        LocalDateTime endDate = LocalDateTime.now();
        CreateCompleteCallDTO completeData = new CreateCompleteCallDTO(data.beginDate(), data.techLogin(), data.asset(),
                data.assetType(), data.department(), data.firstAnalysis(), solution, endDate);

        when(userRepository.findByLogin("tech01")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> callService.createFinishedCall(userId, completeData))
                .isInstanceOf(UserNotFoundException.class);

        when(userRepository.findByLogin("tech01")).thenReturn(Optional.of(createTech()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> callService.createFinishedCall(userId, completeData))
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    @DisplayName("Should delete an call")
    void deleteCallSuccess() {
        UUID callId = UUID.randomUUID();
        CallModel call = createCall();

        when(callRepository.findById(callId)).thenReturn(Optional.of(call));
        callService.deleteCall(callId);

        verify(callRepository, times(1)).delete(any(CallModel.class));
    }

    @Test
    @DisplayName("Should throw CallNotFoundException if call not found")
    void deleteCallFailure() {
        UUID callId = UUID.randomUUID();
        when(callRepository.findById(callId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> callService.deleteCall(callId))
                .isInstanceOf(CallNotFoundException.class);

    }

    private CallModel createCall() {
        CallModel newCall = new CallModel();
        UserModel user = createUser();
        UserModel tech = createTech();

        newCall.setCreatedBy(user);
        newCall.setAssignedTo(tech);
        newCall.setBeginDate(LocalDateTime.now());
        newCall.setAsset(Assets.DATA);
        newCall.setAssetsType(AssetsType.ANTIVIRUS);
        newCall.setDepartment("TI");
        newCall.setFirstAnalysis("analysed");
        newCall.setCallState(CallState.INCOMPLETE);

        return newCall;
    }

    private CreateCallDTO createCallDTO() {
        return new CreateCallDTO(LocalDateTime.now(), "tech01", Assets.DATA,
                AssetsType.ANTIVIRUS, "TI", "Analise");
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setLogin("user01");
        user.setRole(UserRole.USER);

        return user;
    }

    private UserModel createTech() {
        UserModel tech = new UserModel();
        tech.setLogin("tech01");
        tech.setRole(UserRole.TECH);

        return tech;
    }
}