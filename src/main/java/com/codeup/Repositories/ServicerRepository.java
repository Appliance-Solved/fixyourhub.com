package com.codeup.Repositories;

import com.codeup.Models.Servicer;
import com.codeup.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface ServicerRepository extends CrudRepository<Servicer, Long>{

    @Query("select * from Appointment a where a.servicer = ?1 and a.available = ?2 order by a.date, a.startTime asc")
    Iterable<Servicer> findAllByServicerAndAvailability(User servicer, int appliance_id);

}
