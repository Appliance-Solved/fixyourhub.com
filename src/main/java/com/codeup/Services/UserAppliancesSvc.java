package com.codeup.Services;

import com.codeup.Repositories.UserAppliancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("userAppliancesSvc")
public class UserAppliancesSvc {

    private UserAppliancesRepository userAppliancesDao;

    @Autowired
    public UserAppliancesSvc(UserAppliancesRepository userAppliancesDao){
        this.userAppliancesDao = userAppliancesDao;
    }
}
