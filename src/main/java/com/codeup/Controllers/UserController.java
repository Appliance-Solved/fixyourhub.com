package com.codeup.Controllers;

import com.codeup.Models.User;
import com.codeup.Services.UserRolesSvc;
import com.codeup.Services.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by larryg on 7/5/17.
 */
@Controller
public class UserController {
    private UserSvc userSvc;
    private UserRolesSvc userRolesSvc;


    @Autowired
    public UserController(UserSvc userSvc, UserRolesSvc userRolesSvc){
        this.userSvc = userSvc;
        this.userRolesSvc = userRolesSvc;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/selection")
    public String selection() {
        return "selection";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/user/register")
    public String registerUser(@ModelAttribute User user){
        userSvc.save(user);
        userRolesSvc.setUserRole(user);
        return "user/dashboard";
    }

    @PostMapping("/servicer/register")
    public String registerServicer(@ModelAttribute User user){
        userSvc.save(user);
        userRolesSvc.setServicerRole(user);
        return "servicer/dashboard";
    }

}
