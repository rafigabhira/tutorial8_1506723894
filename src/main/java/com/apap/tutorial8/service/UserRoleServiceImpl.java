package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDb userDb;

    @Override
    public UserRoleModel addUser(UserRoleModel user){
        String pass = encrypt(user.getPassword());
        user.setPassword(pass);
        return userDb.save(user);
    }

    @Override
    public String encrypt(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public int updatePassword(String username, String oldPass, String newPass, String confirmNewPass){
        UserRoleModel user = userDb.findByUsername(username);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean oldPassMatches = passwordEncoder.matches(oldPass, user.getPassword());
        boolean newPassMatches = newPass.equals(confirmNewPass);
        int statusUpdate = 0;
        if(oldPassMatches == false){
            statusUpdate = 1;
        } else if(newPassMatches == false) {
            statusUpdate = 2;
        } else if(oldPassMatches == true && newPassMatches == true){

            boolean containsCharAndDigit = newPass.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$");
            if(containsCharAndDigit && newPass.length() >= 8){
                String encryptedPassword = encrypt(newPass);
                user.setPassword(encryptedPassword);
                userDb.save(user);
                statusUpdate = 3;
            } else  if (!containsCharAndDigit){
                statusUpdate = 4;
            } else if(newPass.length() < 8){
                statusUpdate = 5;
            }
        }
        return statusUpdate;
    }
}
