package com.fatin.moodserver.Service;


import com.fatin.moodserver.Model.UserAccount;
import com.fatin.moodserver.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {


    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo){
        this.userRepo = userRepo;
    }

    public void registerUser(UserAccount userAccount){
        userRepo.save(userAccount);
    }


}
