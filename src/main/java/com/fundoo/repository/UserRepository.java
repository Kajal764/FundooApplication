package com.fundoo.repository;

import com.fundoo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);

    @Modifying
    @Transactional
    @Query(value = "update user_info u set u.password =:password where u.email = :email",nativeQuery = true)
    void updatePassword(@Param("password") String password,@Param("email") String email);

}

