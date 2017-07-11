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

    //Query does not work yet (find all servers that work on a appliance.id)
//    public Iterable<Servicer> findAllServicersByApplianceId(long id){
//        return servicerDao.findServicerByApplianceId(id);
//    }

    public void save(Servicer servicer) {
        servicerDao.save(servicer);
    }

    public Servicer findServicerInfoByUserId(User user){
        System.out.println("entered in ServicerSvc method findServicerInfoByUserId(id)");
        Servicer servicer_info = servicerDao.findServicerByUser(user);
        System.out.println("in ServicerSvc, user = " + user.getUsername() + " servicer_info = "+ servicer_info);
        return servicer_info;
    }
}
