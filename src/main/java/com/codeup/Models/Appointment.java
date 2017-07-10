package com.codeup.Models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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

    public boolean checkIfDateTimePassed (Date date, int startTime) {
        String time24;
        if(startTime <10) {
            time24 = "0" + startTime + ":00:00";
        }else {
            time24 = startTime + ":00:00";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String formatedDate = dateFormat.format(date);
            LocalDate localDate = LocalDate.parse(formatedDate);
            LocalTime localTime = LocalTime.parse(time24);
            LocalDateTime appointment = LocalDateTime.of(localDate, localTime);
        if (appointment.isBefore(LocalDateTime.now())){
            return false;
        }else{
            return true;
        }

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
}
