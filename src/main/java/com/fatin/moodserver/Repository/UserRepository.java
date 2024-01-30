package com.fatin.moodserver.Repository;

import com.fatin.moodserver.Model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    // Custom query methods can be defined here if needed

    // Define a query method to find a user by email
    Optional<UserAccount> findByEmail(String email);
}
