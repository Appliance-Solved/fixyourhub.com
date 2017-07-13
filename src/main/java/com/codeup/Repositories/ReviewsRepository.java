package com.codeup.Repositories;

import com.codeup.Models.Reviews;
import com.codeup.Models.ServiceRecords;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface ReviewsRepository extends CrudRepository<Reviews, Long> {
    Reviews findReviewsByServiceRecords(ServiceRecords record);
}
