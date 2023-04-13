package com.ricardo.enrollmentwebapp.security.user;

import com.ricardo.enrollmentwebapp.email.EmailService;
import com.ricardo.enrollmentwebapp.security.auth.confirmationtoken.ConfirmationToken;
import com.ricardo.enrollmentwebapp.security.auth.confirmationtoken.ConfirmationTokenService;
import com.ricardo.enrollmentwebapp.entities.student.Student;
import com.ricardo.enrollmentwebapp.entities.student.StudentService;
import com.ricardo.enrollmentwebapp.security.auth.registration.RegistrationRequest;
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
    // @Autowired
    private final MyUserRepository myUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
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
    public String signUpUser(RegistrationRequest request) throws Exception
    {
        // Check if the username has a matching Student in the database
        Student student = studentService.findStudentById(request.getUsername());
        if (student == null)
        {
            return "There was no student found in database with id: " + request.getUsername();
        }

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

                return sendEmailToUser(student, token);
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

        return sendEmailToUser(student, token);
    }

    public String sendEmailToUser(Student student, String token)
    {
        String link = "http://localhost:8080/auth/confirm?token=" + token;
        String emailText = buildEmail(student.getId(), link);

        // emailService.send(student.getEmail(), emailText);

        boolean sendSuccess = emailService.sendSync(student.getEmail(), emailText);

        String successMessage =  "Email was sent to " + student.getId() + "'s email. Remember to check your spam folder!";
        String failMessage = "Email could not be sent :c";

        return (sendSuccess) ?  successMessage : failMessage;
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

    private String buildEmail(String name, String link) {
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
}
