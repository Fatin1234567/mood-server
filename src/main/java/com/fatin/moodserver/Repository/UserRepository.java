package com.fatin.moodserver.Repository;

import com.fatin.moodserver.Model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
    // Custom query methods can be defined here if needed
}
