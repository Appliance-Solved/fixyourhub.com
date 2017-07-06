package com.codeup.Models;

import javax.persistence.*;

/**
 * Created by Carlos on 7/5/17.
 */

@Entity
@Table(name="user_roles")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser_id() {
        return user;
    }

    public void setUser(User user) {
        this.user= user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
