package com.codeup.Repositories;

import com.codeup.Models.User;
import com.codeup.Models.UserAppliance;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface UserAppliancesRepository extends CrudRepository<UserAppliance, Long> {
Iterable<UserAppliance> findAllByUser(User user);
}
