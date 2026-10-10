
package com.duoc.backend.appointment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointmentService;

    @InjectMocks
    private AppointmentController appointmentController;

    // PRUEBA 1: Listar todas las citas
    @Test
    void testGetAllAppointments() {

        Appointment cita1 = mock(Appointment.class);
        Appointment cita2 = mock(Appointment.class);

        List<Appointment> citas = List.of(cita1, cita2);

        doReturn(citas)
            .when(appointmentService)
            .getAllAppointments();

        List<Appointment> resultado =
            appointmentController.getAllAppointments();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(citas, resultado);

        verify(appointmentService, times(1))
            .getAllAppointments();
    }

    // PRUEBA 2: Buscar una cita por ID
    @Test
    void testGetAppointmentById() {

        Long id = 1L;
        Appointment cita = mock(Appointment.class);

        when(appointmentService.getAppointmentById(id))
            .thenReturn(cita);

        Appointment resultado =
            appointmentController.getAppointmentById(id);

        assertNotNull(resultado);
        assertSame(cita, resultado);

        verify(appointmentService, times(1))
            .getAppointmentById(id);
    }

    // PRUEBA 3: Crear una cita
    @Test
    void testSaveAppointment() {

        Appointment cita = mock(Appointment.class);

        when(appointmentService.saveAppointment(cita))
            .thenReturn(cita);

        Appointment resultado =
            appointmentController.saveAppointment(cita);

        assertNotNull(resultado);
        assertSame(cita, resultado);

        verify(appointmentService, times(1))
            .saveAppointment(cita);
    }

    // PRUEBA 4: Actualizar una cita
    @Test
    void testUpdateAppointment() {

        Long id = 1L;

        Appointment cita = mock(Appointment.class);
        Appointment citaActualizada = mock(Appointment.class);

        when(appointmentService.updateAppointment(id, cita))
            .thenReturn(citaActualizada);

        Appointment resultado =
            appointmentController.updateAppointment(id, cita);

        assertNotNull(resultado);
        assertSame(citaActualizada, resultado);

        verify(appointmentService, times(1))
            .updateAppointment(id, cita);
    }

    // PRUEBA 5: Eliminar una cita
    @Test
    void testDeleteAppointment() {

        Long id = 1L;

        appointmentController.deleteAppointment(id);

        verify(appointmentService, times(1))
            .deleteAppointment(id);
    }
}
