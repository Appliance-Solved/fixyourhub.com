package com.codeup.Services;

import com.codeup.Models.Appointment;
import com.codeup.Models.User;
import com.codeup.Repositories.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by larryg on 7/9/17.
 */
@Service("appointmentSvc")
public class AppointmentSvc {

    private AppointmentsRepository appointmentDao;

    @Autowired
    public AppointmentSvc(AppointmentsRepository appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public Iterable<Appointment> findAllByServicer(User servicer, boolean available) {
        return appointmentDao.findAllByServicerAndAvailability(servicer, available);
    }

    public Iterable<Appointment> findAllByUser(User user, boolean available) {
        return appointmentDao.findAllByUserAndAvailable(user, available);
    }

    public Appointment findById(long id){
        return appointmentDao.findByid(id);
    }

    public void save(Appointment appointment){
        appointmentDao.save(appointment);
    }

    public void delete(Long id) {
        appointmentDao.delete(id);
    }

    public  Iterable<Appointment> findAll() {
        return appointmentDao.findAll();
    }

}
