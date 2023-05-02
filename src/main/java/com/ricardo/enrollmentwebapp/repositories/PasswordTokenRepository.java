package com.ricardo.enrollmentwebapp.repositories;

import com.ricardo.enrollmentwebapp.entities.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long>
{
    Optional<PasswordToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query( "UPDATE PasswordToken t " +
            "SET t.confirmedAt = ?2 " +
            "WHERE t.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
