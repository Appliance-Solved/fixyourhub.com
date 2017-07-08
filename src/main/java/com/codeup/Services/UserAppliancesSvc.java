package com.codeup.Services;

import com.codeup.Models.User;
import com.codeup.Models.UserAppliance;
import com.codeup.Repositories.UserAppliancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("userAppliancesSvc")
public class UserAppliancesSvc {

    private List<String> applianceTypes = new ArrayList<>();

    public List<String> getApplianceTypes() {
        applianceTypes.add("Refrigerator");
        applianceTypes.add("Stove/ Oven");
        applianceTypes.add("Washer");
        applianceTypes.add("Dryer");
        applianceTypes.add("Dishwasher");
        applianceTypes.add("Microwave");
        applianceTypes.add("Ice machine");
        applianceTypes.add("Other");
        return applianceTypes;
    }

    private UserAppliancesRepository userAppliancesDao;

    @Autowired
    public UserAppliancesSvc(UserAppliancesRepository userAppliancesDao){
        this.userAppliancesDao = userAppliancesDao;
    }

    public void save(UserAppliance userAppliance) {
        userAppliancesDao.save(userAppliance);
    }

    public Iterable<UserAppliance> findAllByUser(User user) {
        return userAppliancesDao.findAllByUser(user);
    }

    public String findApplianceType(int appliance_id) {
        return applianceTypes.get(appliance_id);
    }

    public void delete(Long id){
        userAppliancesDao.delete(id);
    }

}
