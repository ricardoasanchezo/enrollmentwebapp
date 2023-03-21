package com.ricardo.enrollmentwebapp.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, String>
{
    Optional<AppUser> findByUsername(String username);

    @Transactional
    @Modifying
    @Query( "UPDATE AppUser a " +
            "SET a.isEnabled = true " +
            "WHERE a.username = ?1")
    int enableAppUser(String username);
}