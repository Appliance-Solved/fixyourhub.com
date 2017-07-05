package com.codeup.models;


import javax.persistence.*;

/**
 * Created by Carlos on 7/5/17.
 */

@Entity
@Table(name="reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 1000)
    private String comments;

    @Column
    private int rating;

    @Column
    private int pricing;

    @OneToOne
    private ServiceRecords serviceRecords;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPricing() {
        return pricing;
    }

    public void setPricing(int pricing) {
        this.pricing = pricing;
    }

    public ServiceRecords getServiceRecords() {
        return serviceRecords;
    }

    public void setServiceRecords(ServiceRecords serviceRecords) {
        this.serviceRecords = serviceRecords;
    }
}
