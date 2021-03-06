package com.codeup.Models;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by larryg on 7/9/17.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DATE")
    private Date date;

    @Column(nullable = false)
    private int startTime;

    @Column(nullable = false)
    private int stopTime;

    @Column(nullable = false)
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "servicer_id")
    private User servicer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private ServiceRecords serviceRecords;

    public Appointment() {
    }

    public Appointment(Date date, int startTime, int stopTime, boolean available, User servicer, User user) {
        this.date = date;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.available = available;
        this.servicer = servicer;
        this.user = user;
    }

    public Appointment(Date date, int startTime, int stopTime, boolean available, User servicer, User user, ServiceRecords serviceRecords) {
        this.date = date;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.available = available;
        this.servicer = servicer;
        this.user = user;
        this.serviceRecords = serviceRecords;
    }

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMMMM dd, YYYY");
        return dateFormat.format(date);
    }

    public String formatTime(int time) {
        String time24 = time + ":00";
        SimpleDateFormat time24format = new SimpleDateFormat("HH:mm");
        SimpleDateFormat time12format = new SimpleDateFormat("hh:mm a");
        try {
            Date time24new = time24format.parse(time24);
            String time12 = time12format.format(time24new);
            return  time12;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error formatting time";
        }
    }

    public boolean checkIfDateTimePassed (Appointment appointment) {
        String time24;
        int startTime = appointment.getStartTime();
        Date date = appointment.getDate();
        if(startTime <10) {
            time24 = "0" + startTime + ":00:00";
        }else {
            time24 = startTime + ":00:00";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String formatedDate = dateFormat.format(date);
            LocalDate localDate = LocalDate.parse(formatedDate);
            LocalTime localTime = LocalTime.parse(time24);
            LocalDateTime availability = LocalDateTime.of(localDate, localTime);
        if (availability.isBefore(LocalDateTime.now())){
            return false;
        }else{
            return true;
        }

    }

    public boolean startBeforeStopTimeAndWindowMax(int startTime, int stopTime) {
        int max = 8;
        int window = stopTime - startTime;
        if(startTime < stopTime && window <= max) {
            return true;
        }else {
            return false;
        }
    }

    public List<String> findTodayNext7(String format) {
        Date today = new Date();
        Date day = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        List<String> week = new ArrayList<>();
        SimpleDateFormat plannerFormat = new SimpleDateFormat(format);
        week.add(plannerFormat.format(today));
        for(int i = 1; i < 7; i++){
            c.add(Calendar.DATE, 1);
            day = c.getTime();
            week.add(plannerFormat.format(day));
        }
        return week;
    }

    public Iterable<Appointment> filterOutFutureAppointmentsAndCompleteServiceRecords(Iterable<Appointment> appointments) {
        Iterator<Appointment> appointmentsThatPassed = appointments.iterator();
        Appointment appointment =new Appointment();
        while (appointmentsThatPassed.hasNext()) {
            Appointment scheduled = appointmentsThatPassed.next();
            if (appointment.checkIfDateTimePassed(scheduled)) {
                appointmentsThatPassed.remove();
            }else if(scheduled.getServiceRecords().getDesc_service() != null){
                appointmentsThatPassed.remove();
            }
        }
        return appointments;
    }

    public Iterable<Appointment> filterOutFutureAppointmentsAndServiceRecordsNotComplete(Iterable<Appointment> appointments) {
        Iterator<Appointment> appointmentsThatPassed = appointments.iterator();
        Appointment appointment =new Appointment();
        while (appointmentsThatPassed.hasNext()) {
            Appointment scheduled = appointmentsThatPassed.next();
            if (appointment.checkIfDateTimePassed(scheduled)) {
                appointmentsThatPassed.remove();
            }else if (scheduled.getServiceRecords()  != null){
                if(scheduled.getServiceRecords().getDesc_service() == null) {
                    appointmentsThatPassed.remove();
                }
            }else if (scheduled.getServiceRecords() == null){
                appointmentsThatPassed.remove();
            }
        }
        return appointments;
    }

    public Iterable<Appointment> filterOutPastAppointments(Iterable<Appointment> appointments) {
        Iterator<Appointment> appointmentsThatPassed = appointments.iterator();
        Appointment appointment = new Appointment();
        while (appointmentsThatPassed.hasNext()) {
            Appointment scheduled = appointmentsThatPassed.next();
            if (!appointment.checkIfDateTimePassed(scheduled)) {
                appointmentsThatPassed.remove();
            }
        }
        return appointments;
    }



    public List<Appointment> checkDatesetDate(List<Integer> dates, List<Integer> times) throws ParseException {
        List<Appointment> allDates = new ArrayList<>();
        Integer first = null;
        List<String> week = findTodayNext7("YYYY-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        for (int i = 0; i < dates.size(); i++){
            Integer date = dates.get(i);
            Integer time = times.get(i);
            if(!date.equals(first)){
                first = date;
                Appointment appointment1 = new Appointment();
                Date thisDate = format.parse(week.get(date));
                appointment1.setDate(thisDate);
                allDates.add(appointment1);
            }
        }
        return allDates;
    }

    public Date findDate(int day) throws ParseException {
        List<String> week = findTodayNext7("dd-M-yyyy hh:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date = format.parse(week.get(day));
        return date;
    }

    public int findHigh(List<Integer> times){
        int max = times.get(0);
        for(Integer time : times){
            if(time > max){
                max = time;
            }
        }
        return max;
    }

    public int findLow(List<Integer> times){
        int min = times.get(0);
        for(Integer time : times){
            if(time < min){
                min = time;
            }
        }
        return min;
    }

    public Iterable<Appointment> filterByIfReviewed(Iterable<Appointment> appointments, boolean ifreviewed){
        Iterator<Appointment> appointmentIterator = appointments.iterator();
        while (appointmentIterator.hasNext()) {
            Appointment scheduled = appointmentIterator.next();

                boolean reviewExist = scheduled.getServiceRecords().getReview() != null;
                if (reviewExist != ifreviewed) {
                    appointmentIterator.remove();
                }

        }
        return appointments;
    }

    public int countAppointments(Iterable<Appointment> appointments) {
        Iterator<Appointment> counter = appointments.iterator();
        int sum = 0;
        while (counter.hasNext()){
            counter.next();
            sum++;
        }
        return sum;
    }

    public Iterable<Appointment> filterOutNonRequested(Iterable<Appointment> appointments){
        Iterator<Appointment> appointmentIterator = appointments.iterator();
        while(appointmentIterator.hasNext()){
            Appointment appointment = appointmentIterator.next();
            if(appointment.getUser() == null){
                appointmentIterator.remove();
            }
        }
        return appointments;
    }

    public List<Reviews> findAllReviewsforServicer(Iterable<Appointment> appointments, User servicer){
        Iterator<Appointment> filteredAppointments = appointments.iterator();
        List<Reviews> reviews = new ArrayList<>();
        while(filteredAppointments.hasNext()){
            Appointment appointment = filteredAppointments.next();
            if(appointment.getServicer().getId() == servicer.getId()){
               reviews.add(appointment.getServiceRecords().getReview());
            }
        }
        return reviews;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getStopTime() {
        return stopTime;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public User getServicer() {
        return servicer;
    }

    public void setServicer(User servicer) {
        this.servicer = servicer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceRecords getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(ServiceRecords serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
}
