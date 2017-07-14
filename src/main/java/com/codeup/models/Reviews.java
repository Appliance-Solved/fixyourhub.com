package com.codeup.Models;


import javax.persistence.*;

import java.util.ArrayList;

import java.util.List;

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

    public List<Reviews> findAllReviewsServicer(Iterable<Appointment> appointments){
        List<Reviews> servicerReviews = new ArrayList<>();
        for(Appointment appointment: appointments){
            Reviews review = (appointment.getServiceRecords().getReview());
            if(review != null){
                servicerReviews.add(review);
            }
        }
        return servicerReviews;
    }

    public double findReviewAvg(List<Reviews> reviewsList){
        double sum = 0;
        int i = 0;
        for(Reviews review: reviewsList){
            sum += review.getRating();
            i++;
        }
        double avg =  sum / i;
        double roundOff = Math.round(avg * 10.0) / 10.0;
        return roundOff;
    }

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
