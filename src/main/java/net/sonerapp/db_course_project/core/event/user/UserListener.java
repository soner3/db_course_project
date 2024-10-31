package net.sonerapp.db_course_project.core.event.user;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import net.sonerapp.db_course_project.core.email.EmailService;
import net.sonerapp.db_course_project.core.model.Profile;
import net.sonerapp.db_course_project.core.model.User;
import net.sonerapp.db_course_project.core.model.UserToken;
import net.sonerapp.db_course_project.core.model.model_enums.UserTokenType;
import net.sonerapp.db_course_project.core.repository.ProfileRepository;
import net.sonerapp.db_course_project.core.repository.UserTokenRepository;

@Service
public class UserListener {

    private static final Logger log = LoggerFactory.getLogger(UserListener.class);

    private final ProfileRepository profileRepository;

    private final EmailService emailService;

    private final UserTokenRepository userTokenRepository;

    @Value("${frontend.url}")
    private String frontendUrl;

    public UserListener(ProfileRepository profileRepository, EmailService emailService,
            UserTokenRepository userTokenRepository) {
        this.profileRepository = profileRepository;
        this.emailService = emailService;
        this.userTokenRepository = userTokenRepository;
    }

    @EventListener
    @Async
    public void createProfileFromUser(UserCreatedEvent event) {
        User user = event.user();
        Profile newProfile = new Profile(user);

        profileRepository.save(newProfile);
        log.info("Profile created for User: {}", user.getFirstname() + " " + user.getLastname());

        String activateUrl = getMailUrlFromUser(event.user(), UserTokenType.USER_ACTIVATION_TOKEN);

        emailService.sendActivateUserMail(user.getEmail(), activateUrl);
        log.info("Activation Mail sended to: {}", user.getEmail());

    }

    @EventListener
    @Async
    public void sendResetPasswordMail(PasswordResetRequestEvent event) {
        User user = event.user();

        String resetUrl = getMailUrlFromUser(user, UserTokenType.PASSWORD_RESET_TOKEN);

        emailService.sendPasswordResetMail(user.getEmail(), resetUrl);
        log.info("Activation Mail sended to: {}", user.getEmail());

    }

    @EventListener
    @Async
    public void resendUserActivationMail(ResendActivationMailEvent event) {
        User user = event.user();
        String activateUrl = getMailUrlFromUser(user, UserTokenType.USER_ACTIVATION_TOKEN);

        emailService.sendActivateUserMail(user.getEmail(), activateUrl);
        log.info("Activation Mail sended to: {}", user.getEmail());

    }

    private String getMailUrlFromUser(User user, UserTokenType tokenType) {
        String token = UUID.randomUUID().toString();

        UserToken userToken = new UserToken(token, user, tokenType);

        UserToken createdToken = userTokenRepository.save(userToken);
        log.info("User Token created for User: {}", user.getFirstname() + " " + user.getLastname());

        return frontendUrl + "/" + createdToken.getToken();

    }

}
