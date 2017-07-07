package com.codeup.Repositories;

import com.codeup.Models.User;
import com.codeup.Models.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Carlos on 7/5/17.
 */
public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
    @Query("select ur.role from UserRole ur, User u where u.username=?1")
    List<String> ofUserWith(String username);

    UserRole findUserRolesByUser(User user);


}
