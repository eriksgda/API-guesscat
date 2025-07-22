package com.eriksgda.guessCat.infra.security;

import com.eriksgda.guessCat.model.cats.Cat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public final class CustomUserDetails implements UserDetails {

    private final Cat cat;

    public CustomUserDetails(Cat cat) {
        this.cat = cat;
    }

    // getter
    public Cat getCat() {
        return this.cat;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = this.cat.getRole().name();
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return this.cat.getPassword();
    }

    @Override
    public String getUsername() {
        return this.cat.getUsername();
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
}
