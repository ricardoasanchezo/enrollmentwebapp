package com.ricardo.enrollmentwebapp.services;

import com.ricardo.enrollmentwebapp.entities.MyUser;
import com.ricardo.enrollmentwebapp.entities.PasswordToken;
import com.ricardo.enrollmentwebapp.repositories.MyUserRepository;
import com.ricardo.enrollmentwebapp.repositories.PasswordTokenRepository;
import com.ricardo.enrollmentwebapp.utils.InputValidator;
import com.ricardo.enrollmentwebapp.utils.Role;
import com.ricardo.enrollmentwebapp.entities.ConfirmationToken;
import com.ricardo.enrollmentwebapp.entities.Student;
import com.ricardo.enrollmentwebapp.utils.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class MyUserService implements UserDetailsService
{
    private final MyUserRepository myUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordTokenService passwordTokenService;
    private final PasswordTokenRepository passwordTokenRepository;
    private final StudentService studentService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return myUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username " + username + " not found!"));
    }

    /**
     * Verifies that the user has a matching student entry in the database, signs them up,
     * creates a token for account verification and send email with verification link.
     * @param request The app user to sign up, received from the request made by the RegistrationService class.
     * @return The token the user needs to confirm for their account validation,
     * or a warning if the user already exists.
     */
    public String register(RegistrationRequest request) throws Exception
    {
        String studentId = request.getUsername();
        if (!InputValidator.matchesRegex(studentId, InputValidator.STUDENT_ID_REGEX))
            return "Student ID invalid!";

        // Check if the username has a matching Student in the database
        Student student = studentService.findStudentById(request.getUsername());
        if (student == null)
            return "There was no student found in database with id: " + request.getUsername();

        MyUser existingMyUser = myUserRepository.findByUsername(request.getUsername()).orElse(null);
        if (existingMyUser != null)
        {
            if (existingMyUser.isEnabled())
            {
                return "User " + existingMyUser.getUsername() + " has already been enabled!";
            }
            else
            {
                // Create and save new token for account verification
                String token = UUID.randomUUID().toString();
                ConfirmationToken confirmationToken = new ConfirmationToken(
                        token,
                        LocalDateTime.now(),
                        LocalDateTime.now().plusMinutes(15),
                        existingMyUser
                );
                confirmationTokenService.saveConfirmationToken(confirmationToken);

                return sendConfirmationEmail(student, token);
            }
        }

        MyUser newUser = new MyUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER,
                false
        );

        // Save user in database
        myUserRepository.save(newUser);

        // Create and save token for account verification
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            newUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return sendConfirmationEmail(student, token);
    }

    public String sendConfirmationEmail(Student student, String token)
    {
        String link = "http://localhost:8080/auth/confirm?token=" + token;
        String emailText = buildRegisterEmail(student.getId(), link);

        boolean sendSuccess = emailService.sendSync(student.getEmail(), emailText, "Confirm your email");

        String successMessage =  "Email was sent to " + student.getId() + "'s email. Remember to check your spam folder!";
        String failMessage = "Email could not be sent :c";

        return (sendSuccess) ?  successMessage : failMessage;
    }

    /**
     * Receives token to confirm from the Registration Controller, checks whether it exists in the database,
     * and checks the expiration time. If the token is valid, it gets confirmed and the user gets enabled.
     *
     * @param token Token to confirm, received by the RegistrationController.
     */
    public void confirmToken(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null)
        {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
        {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        enableAppUser(confirmationToken.getMyUser().getUsername());
    }

    /**
     * Enables the user that matches the received username through the AppUserRepository.
     *
     * @param username The username of the user to enable.
     */
    public void enableAppUser(String username)
    {
        myUserRepository.enableAppUser(username);
    }

    private String buildRegisterEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private String buildResetEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset your password</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Please click on the below link to reset your password: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset your password here</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public String sendResetEmail(String username)
    {
        if (!InputValidator.matchesRegex(username, InputValidator.STUDENT_ID_REGEX))
            return "Student ID invalid!";

        MyUser user = myUserRepository.findByUsername(username).orElse(null);
        if (user == null)
            return "User does not exist!";

        PasswordToken passwordToken = new PasswordToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        passwordTokenService.savePasswordToken(passwordToken);

        Student student = studentService.findStudentById(user.getUsername());

        return sendResetEmail(student, passwordToken.getToken());
    }

    public String sendResetEmail(Student student, String token)
    {
        String link = "http://localhost:8080/auth/resetPassword?token=" + token;
//        String email = String.format("""
//                <h1>Hello, %s!</h1>
//                <p>Reset your password using this link. It will expire shortly so hurry up!</p>
//                <a href="%s">Reset your password here!</a>
//                """,
//                student.getId(), link
//        );

        String email = buildResetEmail(student.getId(), link);

        boolean sendSuccess = emailService.sendSync(student.getEmail(), email, "Reset your password");

        String successMessage =  "Email was sent to " + student.getId() + "'s email. Remember to check your spam folder!";
        String failMessage = "Email could not be sent :c";

        return (sendSuccess) ? successMessage : failMessage;
    }

    public String resetPassword(String token, String password)
    {
        PasswordToken passwordToken = passwordTokenService.getToken(token).orElse(null);

        if (passwordToken == null)
            return "There was an error. Password was not updated!";

        if (passwordToken.getExpiresAt().isBefore(LocalDateTime.now()))
            return "This password reset request is expired. Please make a new password reset request.";

        if (passwordToken.getConfirmedAt() != null)
            return "This password reset request has already been used!";

        myUserRepository.updatePassword(passwordToken.getMyUser().getUsername(), passwordEncoder.encode(password));
        passwordTokenRepository.updateConfirmedAt(passwordToken.getToken(), LocalDateTime.now());

        return "Password was updated successfully!";
    }
}
