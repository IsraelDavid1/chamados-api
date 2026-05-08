package com.company.servicedesk.repositories;

import com.company.servicedesk.dtos.CreateCompleteCallDTO;
import com.company.servicedesk.models.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class CallRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    CallRepository callRepository;

    @Test
    @DisplayName("should get calls in a specific month")
    void findByMonthSucess() {
        CreateCompleteCallDTO data = new CreateCompleteCallDTO(LocalDate.now(), "Israel", Assets.DATA,
                AssetsType.ANTIVIRUS, "TI", "virus detectado", "virus apagado",
                LocalDate.now());
        UserModel user = createUser();

        this.createCall(user, data);

        List<CallModel> result = callRepository.findByMonth(LocalDate.now(), LocalDate.now());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAsset()).isEqualTo(Assets.DATA);
    }

    @Test
    void findByMonthFailure() {
        List<CallModel> result = callRepository.findByMonth(LocalDate.now(), LocalDate.now());

        assertThat(result);
    }

    @Test
    void findByUser() {
    }

    private CallModel createCall(UserModel user, CreateCompleteCallDTO data) {
        CallModel newCall = new CallModel();

        newCall.setCreatedBy(user);
        newCall.setBeginDate(data.beginDate());
        newCall.setAssignedTo(data.tech());
        newCall.setAsset(data.asset());
        newCall.setAssetsType(data.assetType());
        newCall.setDepartment(data.department());
        newCall.setFirstAnalysis(data.firstAnalysis());
        newCall.setSolution(data.solution());
        newCall.setEndDate(data.endDate());
        newCall.setCallState(CallState.COMPLETE);

        this.entityManager.persist(newCall);
        return newCall;
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setLogin("tecnico01");
        user.setPassword("hash");
        user.setDepartment("TI");
        user.setRole(UserRole.TECH);
        entityManager.persist(user);
        return user;
    }
}