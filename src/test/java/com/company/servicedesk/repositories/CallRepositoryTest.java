package com.company.servicedesk.repositories;

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
    private int userCount = 0;

    @Test
    @DisplayName("should get calls in a specific month")
    void findByMonthSuccess() {
        UserModel user = createUser();
        UserModel tech = createUser();
        this.createCall(user, tech);

        List<CallModel> result = callRepository.findByMonth(user.getId(), LocalDate.now(), LocalDate.now());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAsset()).isEqualTo(Assets.DATA);
    }

    @Test
    void findByMonthFailure() {
        UserModel user = createUser();
        List<CallModel> result = callRepository.findByMonth(user.getId() ,LocalDate.now(), LocalDate.now());

        assertThat(result).isEmpty();
    }

    @Test
    void findByUserIdSuccess() {
        UserModel user = createUser();
        UserModel tech = createUser();
        this.createCall(user, tech);

        List<CallModel> result = callRepository.findByUserId(user.getId());

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAsset()).isEqualTo(Assets.DATA);
    }

    @Test
    void findByUserIdFailure() {
        UserModel user = createUser();

        List<CallModel> result = callRepository.findByUserId(user.getId());

        assertThat(result).isEmpty();
    }


    private CallModel createCall(UserModel user, UserModel tech) {
        CallModel newCall = new CallModel();

        newCall.setCreatedBy(user);
        newCall.setAssignedTo(tech);
        newCall.setBeginDate(LocalDate.now());
        newCall.setAsset(Assets.DATA);
        newCall.setAssetsType(AssetsType.ANTIVIRUS);
        newCall.setDepartment("TI");
        newCall.setFirstAnalysis("analisado");
        newCall.setSolution("solucionado");
        newCall.setEndDate(LocalDate.now());
        newCall.setCallState(CallState.COMPLETE);

        this.entityManager.persist(newCall);
        return newCall;
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setLogin("tecnico" + (++userCount));
        user.setPassword("hash");
        user.setDepartment("TI");
        user.setRole(UserRole.TECH);
        entityManager.persist(user);
        return user;
    }
}