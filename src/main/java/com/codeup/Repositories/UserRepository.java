package com.codeup.Repositories;

import com.codeup.Models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Carlos on 7/5/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);

}
