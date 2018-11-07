package com.apap.tutorial8.service;

import com.apap.tutorial8.model.UserRoleModel;

public interface UserRoleService {
    UserRoleModel addUser(UserRoleModel user);
    public String encrypt(String password);
    int updatePassword(String username, String oldPass, String newPass, String confirmNewPass);
}
