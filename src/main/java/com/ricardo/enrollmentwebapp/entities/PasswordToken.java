package com.ricardo.enrollmentwebapp.entities;

import com.ricardo.enrollmentwebapp.user.MyUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbl_password_tokens")
public class PasswordToken
{
    @SequenceGenerator(
            name = "password_token_sequence",
            sequenceName = "password_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "password_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    // @Column(nullable = true)
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "app_user_id"
    )
    private MyUser myUser;


    public PasswordToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             MyUser myUser)
    {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.myUser = myUser;
    }
}
