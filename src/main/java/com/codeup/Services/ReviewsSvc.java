package com.codeup.Services;

import com.codeup.Models.Reviews;
import com.codeup.Repositories.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("reviewsSvc")
public class ReviewsSvc {

    private ReviewsRepository reviewsDao;

    @Autowired
    public ReviewsSvc(ReviewsRepository reviewsDao){
        this.reviewsDao = reviewsDao;
    }

    public void save(Reviews review){
        reviewsDao.save(review);
    }


}
