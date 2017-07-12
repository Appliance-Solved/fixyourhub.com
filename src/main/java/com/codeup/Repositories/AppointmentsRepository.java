package com.codeup.Repositories;

import com.codeup.Models.Appointment;
import com.codeup.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by larryg on 7/9/17.
 */
public interface AppointmentsRepository extends CrudRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.servicer = ?1 and a.available = ?2 order by a.date, a.startTime asc, a.stopTime asc")
    Iterable<Appointment> findAllByServicerAndAvailability(User servicer, boolean available);

    @Query("select a from Appointment a where a.user = ?1 and a.available = ?2 order by a.date, a.startTime asc, a.stopTime asc")
    Iterable<Appointment> findAllByUserAndAvailable(User user, boolean available);


}