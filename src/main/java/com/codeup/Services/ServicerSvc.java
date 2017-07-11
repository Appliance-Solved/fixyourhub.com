package com.codeup.Services;

import com.codeup.Models.Servicer;
import com.codeup.Models.User;
import com.codeup.Repositories.ServicerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("servicerSvc")
public class ServicerSvc {

    private ServicerRepository servicerDao;

    @Autowired
    public ServicerSvc(ServicerRepository servicerDao){
        this.servicerDao = servicerDao;
    }

    public Servicer findOne(long id) {
        return servicerDao.findOne(id);
    }

    public Iterable<User> findAllServicersByApplianceId(long id){
        return servicerDao.findServicerByApplianceId(id);
    }

    public void save(Servicer servicer) {
        servicerDao.save(servicer);
    }

    public Servicer findServicerInfoByUserId(User user){
        Servicer servicer_info = servicerDao.findServicerByUser(user);
        return servicer_info;
    }
}
