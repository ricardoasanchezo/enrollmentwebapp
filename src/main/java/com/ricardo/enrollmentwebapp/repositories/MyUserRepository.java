package com.ricardo.enrollmentwebapp.repositories;

import com.ricardo.enrollmentwebapp.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface MyUserRepository extends JpaRepository<MyUser, String>
{
    Optional<MyUser> findByUsername(String username);

    @Transactional
    @Modifying
    @Query( """
            UPDATE MyUser a
            SET a.isEnabled = true
            WHERE a.username = ?1
            """)
    void enableAppUser(String username);

    @Transactional
    @Modifying
    @Query( """
            UPDATE MyUser a
            SET a.password = ?2
            WHERE a.username = ?1
            """)
    void updatePassword(String username, String password);
}