package com.codeup.Models;

import javax.persistence.*;

/**
 * Created by larryg on 7/5/17.
 */

@Entity
@Table(name = "technicians")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String picUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
