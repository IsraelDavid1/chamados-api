package com.company.servicedesk.services;

import com.company.servicedesk.repositories.CallRepository;
import com.company.servicedesk.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class CallServiceTest {
    @Mock
    private CallRepository callRepository;
    @Mock
    private UserRepository userRepository;

    //when(callRepository.finduserbyid.tehnreturn(....))
    //verify(callRepository, times(1).save(any()))

    @BeforeEach
    void setup() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a basic call")
    void createCallSuccess() {
    }

    @Test
    @DisplayName("Should not create a basic call without proper arguments")
    void createCallFailure() {
    }

    @Test
    @DisplayName("Should finish a basic call")
    void finishCallSuccess() {
    }

    @Test
    @DisplayName("Should not finish a unexistent call")
    void finishCallFailure() {
    }

    @Test
    void createFinishedCallSuccess() {
    }

    @Test
    void createFinishedCallFailure() {
    }

    @Test
    void deleteCallSuccess() {
    }

    @Test
    void deleteCallFailure() {
    }

    @Test
    void getCallSuccess() {
    }

    @Test
    void getCallFailure() {
    }

    @Test
    void getMyCallsSuccess() {
    }

    @Test
    void getMyCallsFailure() {
    }

    @Test
    void getAssignedCallsByMonthSuccess() {
    }

    @Test
    void getAssignedCallsByMonthFailure() {
    }

    @Test
    void getAllCallsSuccess() {
    }

    @Test
    void getAllCallsFailure() {
    }
}