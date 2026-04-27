package com.company.servicedesk.repositories;

import com.company.servicedesk.models.CallModel;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CallRepository extends JpaRepository<CallModel, UUID> {
}
