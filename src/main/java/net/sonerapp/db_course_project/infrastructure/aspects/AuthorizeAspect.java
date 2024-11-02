package net.sonerapp.db_course_project.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import net.sonerapp.db_course_project.core.model.model_enums.AppRoles;
import net.sonerapp.db_course_project.infrastructure.exceptions.AccessDeniedException;

@Aspect
@Component
@Order(1)
public class AuthorizeAspect {

    private final Logger log = LoggerFactory.getLogger(AuthorizeAspect.class);

    @Before("@annotation(net.sonerapp.db_course_project.infrastructure.annotations.RoleValidator)")
    public void validateAdminRole() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !hasAdminRole(authentication)) {
            throw new AccessDeniedException("Access to this ressource is denied");
        }

    }

    private boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(
                        grantedAuthorities -> grantedAuthorities.getAuthority().equals(AppRoles.ROLE_ADMIN.toString()));
    }

}
