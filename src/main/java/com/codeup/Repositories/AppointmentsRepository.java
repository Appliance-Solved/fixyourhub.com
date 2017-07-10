package com.codeup.Repositories;

import com.codeup.Models.Appointment;
import com.codeup.Models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by larryg on 7/9/17.
 */
public interface AppointmentsRepository extends CrudRepository<Appointment, Long> {
    Iterable<Appointment> findAllByServicerAndAvailableEquals(User servicer, boolean available);
}
