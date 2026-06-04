package com.innowise.authservice.repository;

import com.innowise.authservice.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, UUID> {
    Optional<UserData> findByLogin(String login);
    boolean existsByLogin(String login);
    boolean existsByEmail(String email);
}
