package com.codeup.Services;

import com.codeup.Models.User;
import com.codeup.Models.UserRoles;
import com.codeup.Repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service
public class UserRolesSvc {

    private UserRolesRepository userRolesDao;

    @Autowired
    public UserRolesSvc(UserRolesRepository userRolesDao){
        this.userRolesDao = userRolesDao;
    }

    public void setUserRole(User user){
        UserRoles userRoles = new UserRoles(user);
        userRoles.setRole("user");
        userRolesDao.save(userRoles);
    }

    public void setServicerRole(User user){
        UserRoles userRoles = new UserRoles(user);
        userRoles.setRole("servicer");
        userRolesDao.save(userRoles);
    }
}
