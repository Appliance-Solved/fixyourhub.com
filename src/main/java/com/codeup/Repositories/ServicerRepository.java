package com.codeup.Repositories;

import com.codeup.Models.Servicer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface ServicerRepository extends CrudRepository<Servicer, Long>{

    //Query does not work yet
//    @Query("select u.username from Servicer s join User u on s.user_id = u.id where services like concat('%',?1,'%')")
//    Iterable<Servicer> findServicerByApplianceId(long id);

}

