package com.codeup.Services;

import com.codeup.Models.Technician;
import com.codeup.Models.User;
import com.codeup.Repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void save (Technician technician) {
        technicianDao.save(technician);
    }

    public Iterable<Technician> findAllByUser(User user) {
        return technicianDao.findAllByUser(user);
    }

    public void delete(Long id) {
        technicianDao.delete(id);
    }
}
