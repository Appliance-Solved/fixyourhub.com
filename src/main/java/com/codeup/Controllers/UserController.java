package com.codeup.Controllers;

import com.codeup.Models.User;
import com.codeup.Models.UserAppliance;
import com.codeup.Models.UserRole;
import com.codeup.Services.UserAppliancesSvc;
import com.codeup.Services.UserRolesSvc;
import com.codeup.Services.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private UserAppliancesSvc userAppliancesSvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserSvc userSvc, UserRolesSvc userRolesSvc, UserAppliancesSvc userAppliancesSvc){
        this.userSvc = userSvc;
        this.userRolesSvc = userRolesSvc;
        this.userAppliancesSvc = userAppliancesSvc;
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userSvc.save(user);
        UserRole userRole = new UserRole(user);
        userRole.setRole("USER");
        userRolesSvc.save(userRole);
        return "redirect:/login";
    }

    @PostMapping("/servicer/register")
    public String registerServicer(@ModelAttribute User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userSvc.save(user);
        UserRole userRole = new UserRole(user);
        userRole.setRole("SERVICER");
        userRolesSvc.save(userRole);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboardHandler(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       UserRole roles = userRolesSvc.findRolebyUser(user);
        return "redirect:/" + roles.getRole().toLowerCase() + "/dashboard";
    }

    @GetMapping("/user/myappliances")
    public String userAppliances(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        Iterable<UserAppliance> userAppliances = userAppliancesSvc.findAllByUser(user);
        model.addAttribute("userAppliances",userAppliances);
        UserAppliance userAppliance = new UserAppliance();
        model.addAttribute("appliance", userAppliance);

        return "user/myappliances";
    }

    @PostMapping("/user/myappliance")
    public String addUserAppliance(UserAppliance userappliance) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userappliance.setUser(user);
        userAppliancesSvc.save(userappliance);
        return "redirect:/user/myappliances";
    }


}
