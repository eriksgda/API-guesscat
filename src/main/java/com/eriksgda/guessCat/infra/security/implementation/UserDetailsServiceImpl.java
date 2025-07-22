package com.eriksgda.guessCat.infra.security.implementation;

import com.eriksgda.guessCat.infra.security.CustomUserDetails;
import com.eriksgda.guessCat.model.cats.Cat;
import com.eriksgda.guessCat.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CatRepository repository;

    @Autowired
    public UserDetailsServiceImpl(CatRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cat user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
