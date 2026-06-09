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
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
class CallRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    CallRepository callRepository;

    @Test
    @DisplayName("should get calls in a specific month")
    void findByMonthSuccess() {
        UserModel user = createUser();
        UserModel tech = createUser();
        this.createCall(user, tech);

        List<CallModel> result = callRepository.findByMonth(tech.getId(), LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay(), LocalDate.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .atTime(23, 59, 59));

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAsset()).isEqualTo(Assets.DATA);
    }

    @Test
    @DisplayName("should fail in get calls because there is no call in date")
    void findByMonthFailure() {
        UserModel user = createUser();
        List<CallModel> result = callRepository.findByMonth(user.getId() ,LocalDateTime.now(), LocalDateTime.now());

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("should get user")
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
    @DisplayName("should not get unexistent user")
    void findByUserIdFailure() {
        UserModel user = createUser();

        List<CallModel> result = callRepository.findByUserId(user.getId());

        assertThat(result).isEmpty();
    }

    private void createCall(UserModel user, UserModel tech) {
        CallModel newCall = new CallModel();

        newCall.setCreatedBy(user);
        newCall.setAssignedTo(tech);
        newCall.setBeginDate(LocalDateTime.now());
        newCall.setAsset(Assets.DATA);
        newCall.setAssetsType(AssetsType.ANTIVIRUS);
        newCall.setDepartment("TI");
        newCall.setFirstAnalysis("analysed");
        newCall.setSolution("solved");
        newCall.setEndDate(LocalDateTime.now());
        newCall.setCallState(CallState.COMPLETE);

        this.entityManager.persist(newCall);
    }

    private UserModel createUser() {
        UserModel user = new UserModel();
        user.setLogin("tech" + UUID.randomUUID());
        user.setPassword("hash");
        user.setDepartment("TI");
        user.setRole(UserRole.TECH);
        entityManager.persist(user);
        return user;
    }
}
