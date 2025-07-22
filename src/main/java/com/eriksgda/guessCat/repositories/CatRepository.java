package com.eriksgda.guessCat.repositories;

import com.eriksgda.guessCat.model.cats.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatRepository extends JpaRepository<Cat, UUID> {
    Optional<Cat> findByUsername(String username);
    boolean existsByUsername(String username);
}
