package com.codeup.Controllers;


import org.springframework.stereotype.Controller;
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





}
