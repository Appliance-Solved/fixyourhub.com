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

    Servicer findServicerByUser(User user);
}

