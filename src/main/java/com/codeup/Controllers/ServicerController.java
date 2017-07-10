package com.codeup.Controllers;

import com.codeup.Models.Appointment;
import com.codeup.Models.Servicer;
import com.codeup.Models.Technician;
import com.codeup.Models.User;
import com.codeup.Services.AppointmentSvc;
import com.codeup.Services.ServicerSvc;
import com.codeup.Services.TechnicianSvc;
import com.codeup.Services.UserSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private ServicerSvc servicerSvc;
    private AppointmentSvc appointmentSvc;
    @Value("${file-upload-path}")
    private String uploadPath;


    @Autowired
    public ServicerController(UserSvc userSvc, TechnicianSvc techsvc, ServicerSvc servicerSvc, AppointmentSvc appointmentSvc) {
        this.userSvc = userSvc;
        this.techsvc = techsvc;
        this.servicerSvc = servicerSvc;
        this.appointmentSvc = appointmentSvc;
    }

    @GetMapping("/servicer/tech")
    public String showtechs(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

    @PostMapping("/servicer/tech/delete")
    public String deleteTech(@RequestParam(name = "id") Long id){
        techsvc.delete(id);
        return "redirect:/servicer/tech";
    }

    @GetMapping("/servicer/setprofile")
    public String showServSetProfile(Model model) {
        User user = new User((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("user",user);
        model.addAttribute("servicer", new Servicer());
        return "servicer/setup-profile";
    }

    @PostMapping("/servicer/setprofile")
    public String setServUserProfile(
            @RequestParam(name = "address")String address,
            @RequestParam(name = "city")String city,
            @RequestParam(name = "state")String state,
            @RequestParam(name = "zip")Long zip,
            @RequestParam(name = "phone")String phone,
            @ModelAttribute Servicer servicer,
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
        userSvc.update(address, city, state, zip, phone, user.getId());
        servicer.setPicUrl(filename);
        servicer.setUser(user);
        servicerSvc.save(servicer);
        return "redirect:/servicer/dashboard";
    }

    @GetMapping("/servicer/create-availability")
    public String servicerAvailability(@RequestParam(required = false)boolean past,@RequestParam(required = false)boolean timeconflict , Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        Iterable<Appointment> availability = appointmentSvc.findAllByServicer(user, true);
        
        model.addAttribute("availability", availability);
        model.addAttribute("appointment", new Appointment());
        if (past) {
            String message = "Please specify a date that has not passed.";
            model.addAttribute("error", message);
        }else if(timeconflict){
            String message = "You must specify a window greater than one hour and no more than eight hours.";
            model.addAttribute("error", message);
        }
        return "servicer/create-availability";
    }

    @PostMapping("/servicer/availability")
    public String createAvailability(@ModelAttribute Appointment appointment, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        appointment.setServicer(user);
        if(appointment.startBeforeStopTimeAndWindowMax(appointment.getStartTime(), appointment.getStopTime())){
        if (appointment.checkIfDateTimePassed(appointment.getDate(), appointment.getStartTime())) {
            appointmentSvc.save(appointment);
            return "redirect:/servicer/create-availability";
        }else {
            return "redirect:/servicer/create-availability?past=true";
        }}else{
            return "redirect:/servicer/create-availability?timeconflict=true";
        }

    }

    @PostMapping("/servicer/appointment/delete")
    public String deleteAvailability(@RequestParam(name = "id") Long id) {
        appointmentSvc.delete(id);
        return "redirect:/servicer/create-availability";
    }


}
