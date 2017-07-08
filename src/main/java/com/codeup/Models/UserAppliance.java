package com.codeup.Models;

import javax.persistence.*;

/**
 * Created by Carlos on 7/5/17.
 */

@Entity
@Table(name="user_appliances")
public class UserAppliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int appliance_id;

    @Column
    private String name;

    @Column
    private String model;

    @Column
    private String serial;

    public UserAppliance() {
    }

    public UserAppliance(User user, int appliance_id, String name, String model, String serial) {
        this.user = user;
        this.appliance_id = appliance_id;
        this.name = name;
        this.model = model;
        this.serial = serial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getAppliance_id() {
        return appliance_id;
    }

    public void setAppliance_id(int appliance_id) {
        this.appliance_id = appliance_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
