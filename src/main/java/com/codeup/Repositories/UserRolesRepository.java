package com.codeup.Repositories;

import com.codeup.Models.UserRoles;
//import com.codeup.Models.User;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Carlos on 7/5/17.
 */
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {
//    @Query("select ur.role from UserRoles ur, User u where u.username=?1 and ur.user_id = u.id")
//    public List<String> ofUserWith(String username);


}
