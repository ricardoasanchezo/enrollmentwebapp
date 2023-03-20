package com.ricardo.enrollmentwebapp.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest
{
    private final String studentId;
    private final String password;
}
