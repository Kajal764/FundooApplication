package com.fundoo.repository;

import com.fundoo.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email);

}


//(@Param("noOfCopies") int noOfCopies, @Param("bookIds") int bookIds

//@Query("SELECT u FROM User u WHERE u.status = ?1 and u.name = ?2")
//User findUserByStatusAndName(Integer status, String name);

//@Query("SELECT u FROM User u WHERE u.status = ?1")


//    @Query(value = "select * from book_details where author_name LIKE %:keyword% OR book_name LIKE %:keyword%", nativeQuery = true)
//    List<BookDetails> findByAttribute(@Param("keyword") String keyword);