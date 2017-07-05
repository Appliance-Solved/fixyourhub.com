package com.codeup.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Carlos on 7/5/17.
 */

@Entity
@Table(name="service_records")
public class ServiceRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private int service_id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_appliance_id")
    private UserAppliances userAppliances;

    @Column
    private java.util.Date date;

    @Column(nullable = false, length = 1000)
    private String complaint;

    @Column(nullable = false)
    private String parts_installed;

    @Column(nullable = false)
    private String desc_service;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAppliances getUserAppliances() {
        return userAppliances;
    }

    public void setUserAppliances(UserAppliances userAppliances) {
        this.userAppliances = userAppliances;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getParts_installed() {
        return parts_installed;
    }

    public void setParts_installed(String parts_installed) {
        this.parts_installed = parts_installed;
    }

    public String getDesc_service() {
        return desc_service;
    }

    public void setDesc_service(String desc_service) {
        this.desc_service = desc_service;
    }
}
