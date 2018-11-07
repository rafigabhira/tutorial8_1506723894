package com.apap.tutorial8.controller;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model){
        String pass = user.getPassword();
//        boolean containsNumber = pass.matches(".*\\d+.*");
        boolean containsCharAndDigit = pass.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]+$");
        if(containsCharAndDigit && pass.length() >= 8){
            userService.addUser(user);
            model.addAttribute("message", 0);
        } else  if (!containsCharAndDigit){
            model.addAttribute("message",1);
        } else if(pass.length() < 8){
            model.addAttribute("message",2);
        }
        return "home";
    }

    @RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.POST)
    private String addUserSubmit(@PathVariable(value = "username") String username,
                                 @RequestParam(value = "oldPass") String oldPass,
                                 @RequestParam(value = "newPass") String newPass,
                                 @RequestParam(value = "confirmNewPass") String confirmNewPass,
                                 Model model){

        int successUpdate = userService.updatePassword(username, oldPass, newPass, confirmNewPass);
        model.addAttribute("status", successUpdate);
        return "home";
    }
}
