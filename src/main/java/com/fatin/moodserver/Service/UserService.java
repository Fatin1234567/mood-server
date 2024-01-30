package com.fatin.moodserver.Service;


import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Model.UserInfoResponse;
import com.fatin.moodserver.Model.UserRegistrationResponse;
import com.fatin.moodserver.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepo;
    private final UserInfoService userInfoService;

    public UserService(UserRepository userRepo, UserInfoService userInfoService){

        this.userRepo = userRepo;
        this.userInfoService = userInfoService;
    }

    public void registerUser(UserAccount userAccount){
        userRepo.save(userAccount);
    }

    public boolean userExist(String email){
        return userRepo.findByEmail(email).isPresent();
    }

    public UserRegistrationResponse registerUser(String token) {
        // Decode the token and extract email and username
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(token);


        Optional<UserAccount> existingUser = userRepo.findByEmail(userInfoResponse.getEmail());
        if (existingUser.isPresent()) {
            // User already exists, return false
            return new UserRegistrationResponse(false, userInfoResponse.getEmail(), userInfoResponse.getName());
        }

        // Logic to register the new user
        UserAccount newUser = new UserAccount();
        newUser.setEmail(userInfoResponse.getEmail());
        newUser.setUserName(userInfoResponse.getName());
        userRepo.save(newUser);

        // User successfully registered
        return new UserRegistrationResponse(true, userInfoResponse.getEmail(), userInfoResponse.getName());
    }


}
