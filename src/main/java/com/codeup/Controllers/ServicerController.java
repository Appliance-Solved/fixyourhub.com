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
import java.util.ArrayList;
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
    private ReviewsSvc reviewsSvc;
    @Value("${file-upload-path}")
    private String uploadPath;


    @Autowired
    public ServicerController(UserSvc userSvc, TechnicianSvc techsvc, ServicerSvc servicerSvc, AppointmentSvc appointmentSvc, ServiceRecordsSvc serviceRecordsSvc, ReviewsSvc reviewsSvc) {
        this.userSvc = userSvc;
        this.techsvc = techsvc;
        this.servicerSvc = servicerSvc;
        this.appointmentSvc = appointmentSvc;
        this.serviceRecordsSvc = serviceRecordsSvc;
        this.reviewsSvc = reviewsSvc;
    }

    @GetMapping("/servicer/dashboard")
    public String servicerDash(@RequestParam(required = false) boolean past, @RequestParam(required = false) boolean timeconflict, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);

        Servicer servicer = servicerSvc.findServicerInfoByUserId(user);
        model.addAttribute("servicer", servicer);

        Appointment appointment = new Appointment();
        Reviews review = new Reviews();

        Iterable<Appointment> appointmentsByServicer = appointmentSvc.findAllByServicer(user, false);

        List<Reviews> servicerReviews = review.findAllReviewsServicer(appointmentsByServicer);
        double avg = review.findReviewAvg(servicerReviews);
        model.addAttribute("avgrating", avg);
        model.addAttribute("servicerReviews", servicerReviews);

        appointment.filterOutPastAppointments(appointmentsByServicer);
        model.addAttribute("scheduledAppointments",appointmentsByServicer);

        int totalScheduledAppointments = appointment.countAppointments(appointmentsByServicer);
        model.addAttribute("numberScheduled", totalScheduledAppointments);

        Iterable<Appointment> pendingAppointments = appointmentSvc.findAllByServicer(user, true);
        appointment.filterOutNonRequested(pendingAppointments);
        model.addAttribute("pendingAppointments" , pendingAppointments);

        int totalPending = appointment.countAppointments(pendingAppointments);
        model.addAttribute("numberPending", totalPending);

        Iterable<Appointment> appointmentsNeedingServiceRecords = appointmentSvc.findAllByServicer(user, false);
        appointment.filterOutFutureAppointmentsAndCompleteServiceRecords(appointmentsNeedingServiceRecords);
        model.addAttribute("appointmentsNeedSvcRec", appointmentsNeedingServiceRecords);

        int totalNeedSvcRec = appointment.countAppointments(appointmentsNeedingServiceRecords);
        model.addAttribute("numberNeedSvcRec", totalNeedSvcRec);

        model.addAttribute("record", new ServiceRecords());

        Iterable<Appointment> availabilityCheck = appointmentSvc.findAllByServicer(user, true);
        for (Appointment appointmentcheck : availabilityCheck) {
            if (!appointment.checkIfDateTimePassed(appointmentcheck)) {
                appointmentSvc.delete(appointmentcheck.getId());
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

        Iterable<Technician> team = techsvc.findAllByUser(user);
        model.addAttribute("team", team);
        Technician tech = new Technician();
        model.addAttribute("tech", tech);

        Servicer servicerinfo = servicerSvc.findServicerInfoByUserId(user);
        if(servicerinfo == null){
            servicerinfo = new Servicer();
        }
        model.addAttribute("servicer", servicerinfo);

        return "servicer/dashboard";
    }

    @GetMapping("/servicer/pend")
    public  String showpending(Model model) {
        return "servicer/pending";
    }

    @GetMapping("/servicer/review")
    public String showReview(Model model) {
        return "servicer/reviews";
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
        return "redirect:/servicer/dashboard#myteam";
    }

    @PostMapping("/servicer/tech/delete")
    public String deleteTech(@RequestParam(name = "id") Long id) {
        techsvc.delete(id);
        return "redirect:/servicer/dashboard#myteam";
    }

    @GetMapping("/servicer/setprofile")
    public String showServSetProfile(Model model) {
        User user = new User((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("user", user);
        Servicer servicer = servicerSvc.findServicerInfoByUserId(user);
        if(servicer == null){
            servicer = new Servicer();
        }
        model.addAttribute("servicer", servicer);
        return "servicer/setup-profile";
    }

    @PostMapping("/servicer/setprofile")
    public String setServUserProfile(
            @RequestParam(name = "user.address") String address,
            @RequestParam(name = "user.city") String city,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "user.zipcode") Long zip,
            @RequestParam(name = "user.phone") String phone,
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
        return "redirect:/servicer/dashboard#prof";
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



    @PostMapping("/servicer/appointment/delete")
    public String deleteAvailability(@RequestParam(name = "id") Long id) {
        appointmentSvc.delete(id);
        return "redirect:/servicer/dashboard#avail";
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
                return "redirect:/servicer/dashboard#avail";
            } else {
                return "redirect:/servicer/dashboard?past=true";
            }
        } else {
            return "redirect:/servicer/dashboard?timeconflict=true";
        }
    }


    @GetMapping("/servicer/submit-service")
    public String viewServiceRecordSubmit(Model model) {
        User servicer = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
        Iterable<Appointment> appointmentsByServicer = appointmentSvc.findAllByServicer(servicer, false);
       appointment.filterOutFutureAppointmentsAndCompleteServiceRecords(appointmentsByServicer);
            model.addAttribute("appointments", appointmentsByServicer);
            model.addAttribute("user", servicer);
            model.addAttribute("record", new ServiceRecords());

            return "servicer/submit-servicerecord";
        }

        @PostMapping("/servicer/submit-service")
    public String submitServiceRecord(@ModelAttribute ServiceRecords record, @RequestParam(name = "service_record_id")int id){
        ServiceRecords svcRecord = serviceRecordsSvc.findRecordbyId(id);
        svcRecord.setParts_installed(record.getParts_installed());
        svcRecord.setDesc_service(record.getDesc_service());
        serviceRecordsSvc.save(svcRecord);
            return "redirect:/servicer/dashboard";
        }

        @PostMapping("/servicer/appointment/confirm")
    public String confirmAppointment(@RequestParam(name = "confirm_id")Long id, @RequestParam(name = "tech_id")Long tech_id){
        Technician tech = techsvc.findOneById(tech_id);
        Appointment appointment = appointmentSvc.findById(id);
        Mailer mailer = new Mailer();
        appointment.setAvailable(false);
        appointmentSvc.save(appointment);
        Mailer.send(mailer.getFrom(), mailer.getPassword(), appointment.getUser().getEmail(), mailer.getConfirmSub(), mailer.confirmMsg(appointment, tech));
        return "redirect:/servicer/dashboard";
        }

        @PostMapping("/appointment/decline")
    public String declineAppointment(@RequestParam(name = "decline_id")Long id){
        Appointment appointment = appointmentSvc.findById(id);
        appointment.setUser(null);
        appointment.setServiceRecords(null);
        appointmentSvc.save(appointment);
        return "redirect:/servicer/dashboard";
        }




    }

