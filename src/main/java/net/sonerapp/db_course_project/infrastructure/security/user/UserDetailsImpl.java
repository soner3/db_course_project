package net.sonerapp.db_course_project.infrastructure.security.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.sonerapp.db_course_project.core.model.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserDetailsImpl implements UserDetails {

    private String username;

    private String email;

    private String password;

    private String firstname;

    private String lastname;

    private boolean isEnabled;

    private boolean isCredentialsNonExpired;

    private boolean isAccountNonLocked;

    private boolean isAccountNonExpired;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetails build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRolename().toString());
        System.out.println("oierfg");
        return new UserDetailsImpl(user.getUsername(), user.getEmail(), user.getPassword(), user.getFirstname(),
                user.getLastname(), user.isEnabled(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                user.isAccountNonExpired(), List.of(authority));
    }

}
