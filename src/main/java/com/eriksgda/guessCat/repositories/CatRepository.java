package com.eriksgda.guessCat.repositories;

import com.eriksgda.guessCat.model.cats.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CatRepository extends JpaRepository<Cat, UUID> {
    UserDetails findByUsername(String username);
    boolean existsByUsername(String username);
}
