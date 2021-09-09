package com.finance.api.repository;

import com.finance.api.entity.Incomes;
import com.finance.api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users , Long> {
    Optional<Users> findUserByEmail(String email);

    @Query(value = "SELECT id from users WHERE users.email = ?1",
            nativeQuery = true
    )
    Long findIdByEmail(String email);


    Users findUserById(Long id);
}
