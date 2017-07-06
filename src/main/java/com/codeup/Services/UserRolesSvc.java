package com.codeup.Services;

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
}
