package com.codeup.Controllers;

import com.codeup.Models.*;
import com.codeup.Services.*;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by larryg on 7/7/17.
 */
@Controller
public class ServicerController {
    private UserSvc userSvc;
    private TechnicianSvc techsvc;
    private ServicerSvc servicerSvc;
    private AppointmentSvc appointmentSvc;
    private ServiceRecordsSvc serviceRecordsSvc;
    @Value("${file-upload-path}")
    private String uploadPath;


    @Autowired
    public ServicerController(UserSvc userSvc, TechnicianSvc techsvc, ServicerSvc servicerSvc, AppointmentSvc appointmentSvc, ServiceRecordsSvc serviceRecordsSvc) {
        this.userSvc = userSvc;
        this.techsvc = techsvc;
        this.servicerSvc = servicerSvc;
        this.appointmentSvc = appointmentSvc;
        this.serviceRecordsSvc = serviceRecordsSvc;
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
    public String deleteTech(@RequestParam(name = "id") Long id) {
        techsvc.delete(id);
        return "redirect:/servicer/tech";
    }

    @GetMapping("/servicer/setprofile")
    public String showServSetProfile(Model model) {
        User user = new User((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("user", user);
        model.addAttribute("servicer", new Servicer());
        return "servicer/setup-profile";
    }

    @PostMapping("/servicer/setprofile")
    public String setServUserProfile(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "zip") Long zip,
            @RequestParam(name = "phone") String phone,
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
    public String servicerAvailability(@RequestParam(required = false) boolean past, @RequestParam(required = false) boolean timeconflict, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        Iterable<Appointment> availabilityCheck = appointmentSvc.findAllByServicer(user, true);
        for (Appointment appointment : availabilityCheck) {
            if (!appointment.checkIfDateTimePassed(appointment)) {
                appointmentSvc.delete(appointment.getId());
            }
        }
        Iterable<Appointment> availability = appointmentSvc.findAllByServicer(user, true);
        model.addAttribute("availability", availability);
        model.addAttribute("appointment", new Appointment());
        if (past) {
            String message = "Please specify a date that has not passed.";
            model.addAttribute("error", message);
        } else if (timeconflict) {
            String message = "You must specify a window greater than one hour and no more than eight hours.";
            model.addAttribute("error", message);
        }
        return "servicer/create-availability";
    }

//    @PostMapping("/servicer/availability")
//    public String createAvailability(@ModelAttribute Appointment appointment, Model model){
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        appointment.setServicer(user);
//        if(appointment.startBeforeStopTimeAndWindowMax(appointment.getStartTime(), appointment.getStopTime())){
//        if (appointment.checkIfDateTimePassed(appointment.getDate(), appointment.getStartTime())) {
//            appointmentSvc.save(appointment);
//            return "redirect:/servicer/create-availability";
//        }else {
//            return "redirect:/servicer/create-availability?past=true";
//        }}else{
//            return "redirect:/servicer/create-availability?timeconflict=true";
//        }

//    }

    @PostMapping("/servicer/appointment/delete")
    public String deleteAvailability(@RequestParam(name = "id") Long id) {
        appointmentSvc.delete(id);
        return "redirect:/servicer/create-availability";
    }

    @PostMapping("/servicer/setavailability")
    public String setAvailability(@RequestParam(name = "date") List<Integer> dates, @RequestParam(name = "time") List<Integer> times) throws ParseException {
        User servicer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
//        List<Appointment> appointments =  appointment.checkDatesetDate(dates);
        Date date = appointment.findDate(dates.get(0));
        int start = appointment.findLow(times);
        int stop = appointment.findHigh(times);
        appointment.setDate(date);
        appointment.setStartTime(start);
        appointment.setStopTime(stop + 1);
        appointment.setServicer(servicer);
        appointmentSvc.save(appointment);
        if (appointment.startBeforeStopTimeAndWindowMax(appointment.getStartTime(), appointment.getStopTime())) {
            if (appointment.checkIfDateTimePassed(appointment)) {
                appointmentSvc.save(appointment);
                return "redirect:/servicer/create-availability";
            } else {
                return "redirect:/servicer/create-availability?past=true";
            }
        } else {
            return "redirect:/servicer/create-availability?timeconflict=true";
        }
    }


    @GetMapping("/servicer/submit-service")
    public String viewServiceRecordSubmit(Model model) {
        User servicer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
        Iterable<Appointment> appointmentsByServicer = appointmentSvc.findAllByServicer(servicer, false);
       appointment.filterOutFutureAppointments(appointmentsByServicer);
            model.addAttribute("appointments", appointmentsByServicer);
            model.addAttribute("user", servicer);
            model.addAttribute("record", new ServiceRecords());

            return "servicer/submit-servicerecord";
        }

        @PostMapping("/servicer/submit-service")
    public String submitServiceRecord(@ModelAttribute ServiceRecords record){
        ServiceRecords svcRecord = serviceRecordsSvc.findRecordbyId(record.getId());
        svcRecord.setParts_installed(record.getParts_installed());
        svcRecord.setDesc_service(record.getDesc_service());
        serviceRecordsSvc.save(svcRecord);
            return "redirect:/servicer/submit-service";
        }





    }

