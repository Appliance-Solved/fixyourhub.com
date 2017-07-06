package com.codeup.Services;

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
}
