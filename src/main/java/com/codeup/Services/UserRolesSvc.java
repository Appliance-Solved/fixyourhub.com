package com.codeup.Services;

import com.codeup.Models.User;
import com.codeup.Models.UserRole;
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

    public UserRole findRolebyUser(User user){
        return userRolesDao.findUserRolesByUser(user);
    }

    public void setUserRole(User user){
        UserRole userRole = new UserRole(user);
        userRole.setRole("USER");
        userRolesDao.save(userRole);
    }

    public void setServicerRole(User user){
        UserRole userRole = new UserRole(user);
        userRole.setRole("SERVICER");
        userRolesDao.save(userRole);
    }
}
