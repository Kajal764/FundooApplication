package com.fundoo.repository;

import com.fundoo.model.RegisterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterUserRepository extends JpaRepository<RegisterUser, Integer> {
    Optional<RegisterUser> findByEmail(String email);
}
