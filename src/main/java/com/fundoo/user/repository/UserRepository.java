package com.fundoo.user.repository;

import com.fundoo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update user u set u.password =:password,u.account_updated_date=:time where u.email = :email", nativeQuery = true)
    void updatePasswordAndTime(@Param("password") String password, @Param("time") LocalDateTime time,@Param("email") String email);

}

