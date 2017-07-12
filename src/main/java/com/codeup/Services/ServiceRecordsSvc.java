package com.codeup.Services;

import com.codeup.Models.ServiceRecords;
import com.codeup.Repositories.ServiceRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("serviceRecordsSvc")
public class ServiceRecordsSvc {

    private ServiceRecordsRepository serviceRecordsDao;

    @Autowired
    public ServiceRecordsSvc(ServiceRecordsRepository serviceRecordsDao) {
        this.serviceRecordsDao = serviceRecordsDao;
    }

    public void save(ServiceRecords record) {
        serviceRecordsDao.save(record);
    }

    public ServiceRecords findRecordbyId(int id){
       return serviceRecordsDao.findServiceRecordById(id);
    }

}
