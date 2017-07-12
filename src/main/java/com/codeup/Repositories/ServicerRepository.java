package com.codeup.Repositories;

import com.codeup.Models.Servicer;
import com.codeup.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface ServicerRepository extends CrudRepository<Servicer, Long>{

    //Query does not work yet
    @Query("select s.user from Servicer s where s.services like concat('%',?1,'%')")
    Iterable<User> findServicerByApplianceId(long id);


    @Query(
        nativeQuery = true,
        value= "SELECT u.* FROM users u JOIN servicer_info s ON u.id = s.user_id JOIN appointments a ON s.user_id = a.servicer_id where a.date BETWEEN curdate() AND DATE(DATE_SUB(NOW(), INTERVAL -?1 DAY))"
    )
    Iterable<User> findServicerByAvailability(long inDays);



    Servicer findServicerByUser(User user);
}

