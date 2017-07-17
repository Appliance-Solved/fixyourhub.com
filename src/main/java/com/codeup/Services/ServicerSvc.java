package com.codeup.Services;

import com.codeup.Models.Servicer;
import com.codeup.Models.User;
import com.codeup.Repositories.ServicerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

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

    public Iterable<BigInteger> findServicerByAvailability(long inDays){
//        Iterable<BigInteger> bigInt = servicerDao.findServicerByAvailability(inDays);
        return servicerDao.findServicerByAvailability(inDays);
    }

    public Iterable<Servicer> findAllServicers(){
       return servicerDao.findAll();
    }

    public String printAllServices(String applianceTypeCode){
        List<String> serviceList = Arrays.asList(applianceTypeCode.split(","));
        String printServices = "| ";
        for (String service: serviceList){
            switch(service){
                case "0":
                    printServices += "Refrigerators | ";
                    break;
                case "1":
                    printServices += "Stoves/Ovens | ";
                    break;
                case "2":
                    printServices += "Washers | ";
                    break;
                case "3":
                    printServices += "Dryers | ";
                    break;
                case "4":
                    printServices += "Dishwashers | ";
                    break;
                case "5":
                    printServices += "Microwaves | ";
                    break;
                case "6":
                    printServices += "Ice Machines | ";
                    break;
                case "7":
                    printServices += "Others |";
                    break;
            }
        }
        return printServices;
    }
}
