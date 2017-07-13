package com.codeup.Controllers;

import com.codeup.Models.*;
import com.codeup.Services.*;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by larryg on 7/5/17.
 */
@Controller
public class UserController {
    private UserSvc userSvc;
    private UserRolesSvc userRolesSvc;
    private UserAppliancesSvc userAppliancesSvc;
    private ServicerSvc servicerSvc;
    private AppointmentSvc appointmentSvc;
    private ReviewsSvc reviewsSvc;
    private ServiceRecordsSvc serviceRecordsSvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired

    public UserController(UserSvc userSvc, UserRolesSvc userRolesSvc, UserAppliancesSvc userAppliancesSvc, ServicerSvc servicerSvc, AppointmentSvc appointmentSvc, ReviewsSvc reviewsSvc, ServiceRecordsSvc serviceRecordsSvc){

        this.userSvc = userSvc;
        this.userRolesSvc = userRolesSvc;
        this.userAppliancesSvc = userAppliancesSvc;
        this.servicerSvc = servicerSvc;
        this.appointmentSvc = appointmentSvc;
        this.reviewsSvc = reviewsSvc;
        this.serviceRecordsSvc = serviceRecordsSvc;
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
    public String registerUser(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userSvc.save(user);
        UserRole userRole = new UserRole(user);
        userRole.setRole("USER");
        userRolesSvc.save(userRole);
        return "redirect:/login";
    }

    @PostMapping("/servicer/register")
    public String registerServicer(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userSvc.save(user);
        UserRole userRole = new UserRole(user);
        userRole.setRole("SERVICER");
        userRolesSvc.save(userRole);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboardHandler() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserRole roles = userRolesSvc.findRolebyUser(user);
        return "redirect:/" + roles.getRole().toLowerCase() + "/dashboard";
    }

    @GetMapping("/user/myappliances")
    public String userAppliances(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        Iterable<UserAppliance> userAppliances = userAppliancesSvc.findAllByUser(user);
        model.addAttribute("userAppliances", userAppliances);
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

    @PostMapping("/user/myappliance/delete")
    public String deleteUserAppliance(@RequestParam(name = "id") Long id) {
        userAppliancesSvc.delete(id);
        return "redirect:/user/myappliances";
    }

    @GetMapping("/user/setprofile")
    public String showSetProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user/setup-profile";
    }


    @PostMapping("/user/setprofile")
    public String setUserProfile(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "zip") Long zip,
            @RequestParam(name = "phone") String phone
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userSvc.update(address, city, state, zip, phone, user.getId());
        System.out.println("im out");
        return "redirect:/user/dashboard";
    }

    @GetMapping("/user/scheduleservice")
    public String scheduleService(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        Iterable<UserAppliance> userAppliances = userAppliancesSvc.findAllByUser(user);
        model.addAttribute("userAppliances", userAppliances);
        UserAppliance userAppliance = new UserAppliance();
        model.addAttribute("appliance", userAppliance);
        return "user/schedule-service";
    }

    @GetMapping("/user/scheduleservice/day")
    public String scheduleServiceDate(
            @RequestParam(name = "applianceId") long applianceId,
            Model model
    ) {
        model.addAttribute("applianceId", applianceId);
        return "user/schedule-service-date";
    }

    @GetMapping("/user/scheduleservice/results")
    public String serviceSearchResults(
            @RequestParam(name = "applianceId") long applianceId,
            @RequestParam(name = "time-frame") int timeFrame,
            Model model
    ) {
//        Iterable<User> servicers = servicerSvc.findAllServicersByApplianceId(applianceId);
        Iterable<BigInteger> servicerIds = servicerSvc.findServicerByAvailability(timeFrame);
        List<User> servicers = new ArrayList<>();
        for (BigInteger bigIntId : servicerIds) {
            Long longId = bigIntId.longValue();
            User user = userSvc.findOne(longId);
            Servicer servicer_info = servicerSvc.findServicerInfoByUserId(user);
            String services = servicer_info.getServices();
            boolean match = services.contains(Long.toString(applianceId));
            if (match) {
                servicers.add(user);
            }
        }
        model.addAttribute("servicers", servicers);
        return "user/servicers-results";

    }

    @GetMapping("/user/viewservicer")
    public String showServicerProfile(@RequestParam(name = "id") long id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        User servicer = userSvc.findOne(id);
        model.addAttribute("servicer", servicer);
        Servicer servicer_info = servicerSvc.findServicerInfoByUserId(servicer);
        model.addAttribute("servicer_info", servicer_info);
        System.out.println("servicer_info: " + servicer_info.getServices());
        return "user/viewservicer";
    }

    @GetMapping("/user/service-records")
    public String viewServiceRecord(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
        Iterable<Appointment> appointmentsByUser = appointmentSvc.findAllByUser(user, false);
        appointment.filterOutFutureAppointmentsAndServiceRecordsNotComplete(appointmentsByUser);
        model.addAttribute("appointments", appointmentsByUser);
        model.addAttribute("user", user);
        model.addAttribute("record", new ServiceRecords());

        return "user/view-service-records";
    }

    @GetMapping("/user/reviews")
    public String userReviews(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Appointment appointment = new Appointment();
        Iterable<Appointment> needReviews = appointmentSvc.findAllByUser(user, false);
        appointment.filterOutFutureAppointmentsAndServiceRecordsNotComplete(needReviews);
        appointment.filterByIfReviewed(needReviews, false);
        model.addAttribute("needreviews", needReviews);
        model.addAttribute("review", new Reviews());
        return "user/reviews";
    }

        @PostMapping("/user/review")
    public String submitReview(@ModelAttribute Reviews review, @RequestParam(name = "service_record_id") int id) {
            System.out.println(id);
            ServiceRecords record = serviceRecordsSvc.findRecordbyId(id);

        review.setServiceRecords(record);
        reviewsSvc.save(review);
        Reviews setterReview = reviewsSvc.findOneByServiceRecord(record);
        record.setReview(setterReview);
        serviceRecordsSvc.save(record);
        return "redirect:/user/reviews";
        }

}
