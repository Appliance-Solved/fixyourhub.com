package com.codeup.Models;

import javax.persistence.*;

/**
 * Created by Carlos on 7/5/17.
 */

@Entity
@Table(name="service_records")
public class ServiceRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_appliance_id")
    private UserAppliance userAppliance;

    @Column(nullable = false, length = 1000)
    private String complaint;

    @Column()
    private String parts_installed;

    @Column(length = 2000)
    private String desc_service;

    @OneToOne
    private Reviews review;

    public ServiceRecords() {
    }

    public ServiceRecords(String complaint, UserAppliance userAppliance){
        this.complaint = complaint;
        this.userAppliance = userAppliance;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserAppliance getUserAppliance() {
        return userAppliance;
    }

    public void setUserAppliance(UserAppliance userAppliance) {
        this.userAppliance = userAppliance;
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

    public Reviews getReview() {
        return review;
    }

    public void setReview(Reviews review) {
        this.review = review;
    }
}
