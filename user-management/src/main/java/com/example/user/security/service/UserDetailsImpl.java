package com.example.user.security.service;

import com.example.user.entity.Role;
import com.example.user.entity.User;
import java.util.Collection;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 *
 * @author AnuragSingh
 */
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final String userId;

    private final String username;

    private final String email;

    @JsonIgnore
    private final String password;

    private final Set<Role> roles;
    //private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String userId, String username, String email, String password, Set<Role> roles) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles());
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
//            role.getPrivileges().stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getName()))
//                    .forEach(authorities::add);
        }
        return authorities;
    }
}
