package com.codeup.Models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by larryg on 7/5/17.
 */
@Entity
@Table(name = "servicer_info")
public class Servicer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long serviceCallFee;

    @Column(length = 2000)
    private String about;

    @Column(nullable = false)
    private String services;

    @Column
    private String picUrl;

    @OneToOne
    private User user;


    public Servicer() {
    }

    public Servicer(Long serviceCallFee, String about, String services, String picUrl, User user) {
        this.serviceCallFee = serviceCallFee;
        this.about = about;
        this.services = services;
        this.picUrl = picUrl;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceCallFee() {
        return serviceCallFee;
    }

    public void setServiceCallFee(Long serviceCallFee) {
        this.serviceCallFee = serviceCallFee;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
