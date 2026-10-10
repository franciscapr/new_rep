package com.duoc.backend.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Iterable<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
    Appointment appointment = appointmentRepository.findById(id).orElse(null);

    if (appointment == null) {
        return null;
    }

    appointment.setDate(appointmentDetails.getDate());
    appointment.setTime(appointmentDetails.getTime());
    appointment.setReason(appointmentDetails.getReason());
    appointment.setVeterinarian(appointmentDetails.getVeterinarian());

    return appointmentRepository.save(appointment);
}

    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}