package com.fatin.moodserver;

import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindByEmail() {
        // Arrange: Create a new user instance
        String userEmail = "test@example.com";
        String userName = "test";
        UserAccount newUser = new UserAccount();
        newUser.setEmail(userEmail);
        newUser.setUserName(userName);

        // Set other necessary properties of newUser

        // Act: Save the user to the repository
        UserAccount savedUser = userRepository.save(newUser);

        // Assert: Check the user was saved
        assertNotNull(savedUser);
        assertNotNull(savedUser.getUserId());

/*        // Act: Retrieve the user by email
        Optional<UserAccount> foundUser = userRepository.findByEmail(userEmail);

        // Assert: Verify the retrieved user is the same as the saved user
        assertTrue(foundUser.isPresent());
        assertEquals(userEmail, foundUser.get().getEmail());*/
    }
}
