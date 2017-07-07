package com.codeup.Repositories;

import com.codeup.Models.Technician;
import com.codeup.Models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface TechnicianRepository extends CrudRepository<Technician, Long> {
        Iterable<Technician> findAllByUser(User user);
}
