package com.ricardo.enrollmentwebapp.security.token;

import java.util.List;
import java.util.Optional;

import com.ricardo.enrollmentwebapp.security.token.JWToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JWTokenRepository extends JpaRepository<JWToken, Integer>
{
//  @Query("""
//      SELECT t from JWToken t inner join User u\s
//      on t.user.id = u.id\s
//      where u.id = :id and (t.expired = false or t.revoked = false)\s
//      """)
//  List<JWToken> findAllValidTokenByUser(Integer id);

  Optional<JWToken> findByToken(String token);
}
