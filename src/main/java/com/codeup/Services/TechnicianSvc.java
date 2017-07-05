package com.codeup.Services;

import com.codeup.Repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Carlos on 7/5/17.
 */

@Service("technicianSvc")
public class TechnicianSvc {

    private TechnicianRepository technicianDao;

    @Autowired
    public TechnicianSvc(TechnicianRepository technicianDao){
        this.technicianDao = technicianDao;
    }
}
