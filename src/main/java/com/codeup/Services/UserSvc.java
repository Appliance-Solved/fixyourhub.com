package com.codeup.Services;

import com.codeup.Models.User;
import com.codeup.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("userSvc")
public class UserSvc {

    private UserRepository userDao;

    @Autowired
    public UserSvc(UserRepository userDao) {
        this.userDao = userDao;
    }

    public User findOne(long id) {
        return userDao.findOne(id);
    }



}
