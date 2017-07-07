package com.codeup.Controllers;


import com.codeup.Models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by larryg on 7/6/17.
 */
@Controller
public class AuthenticationController {



    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/user/dashboard")
    public String userDash(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/dashboard";
    }

    @GetMapping("/servicer/dashboard")
    public String servicerDash(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "servicer/dashboard";
    }

}
