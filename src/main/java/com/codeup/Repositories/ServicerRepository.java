package com.codeup.Repositories;

import com.codeup.Models.Servicer;
import com.codeup.Models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

/**
 * Created by Carlos on 7/5/17.
 */
public interface ServicerRepository extends CrudRepository<Servicer, Long>{

    @Query("select s.user from Servicer s where s.services like concat('%',?1,'%')")
    Iterable<User> findServicerByApplianceId(long id);


    @Query(
        nativeQuery = true,
        value= "SELECT u.id FROM users u JOIN servicer_info s ON u.id = s.user_id JOIN appointments a ON s.user_id = a.servicer_id where a.date BETWEEN curdate() AND DATE(DATE_SUB(NOW(), INTERVAL -?1 DAY)) GROUP BY u.id"
    )
    Iterable<BigInteger> findServicerByAvailability(Long inDays);



    Servicer findServicerByUser(User user);
}

