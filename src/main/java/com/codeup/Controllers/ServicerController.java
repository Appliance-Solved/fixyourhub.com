package com.codeup.Controllers;

import com.codeup.Models.Technician;
import com.codeup.Models.User;
import com.codeup.Services.TechnicianSvc;
import com.codeup.Services.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by larryg on 7/7/17.
 */
@Controller
public class ServicerController {
    private UserSvc userSvc;
    private TechnicianSvc techsvc;
    @Value("${file-upload-path}")
    private String uploadPath;


    @Autowired
    public ServicerController(UserSvc userSvc, TechnicianSvc techsvc) {
        this.userSvc = userSvc;
        this.techsvc = techsvc;
    }

    @GetMapping("/servicer/tech")
    public String showtechs(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        System.out.println(user.getName());
        System.out.println(user.getUsername());
        model.addAttribute("user", user);
        Iterable<Technician> team = techsvc.findAllByUser(user);
        model.addAttribute("team", team);
        Technician tech = new Technician();
        model.addAttribute("tech", tech);
        return "servicer/technician";
    }

    @PostMapping("servicer/tech")
    public String addtech(
            Technician tech,
            @RequestParam(name = "file") MultipartFile uploadedFile
    ) {
        String filename = uploadedFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);
        try {
            uploadedFile.transferTo(destinationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tech.setPicUrl(filename);
        tech.setUser(user);
        techsvc.save(tech);
        return "redirect:/servicer/tech";
    }
}
