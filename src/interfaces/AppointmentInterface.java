package interfaces;

import exceptions.AppointmentNotFoundException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.UpdateException;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface AppointmentInterface {

    public <T> T FindAllAppointments_XML(GenericType<T> responseType) throws AppointmentNotFoundException;

    public <T> T FindAllAppointments_JSON(Class<T> responseType) throws AppointmentNotFoundException;

    public void UpdateAppointment_XML(Object requestEntity) throws UpdateException;

    public void updateAppointment_JSON(Object requestEntity) throws UpdateException;

    public <T> T FindAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws AppointmentNotFoundException;

    public <T> T FindAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws AppointmentNotFoundException;

    public void createAppointment_XML(Object requestEntity) throws CreateException;

    public void createAppointment_JSON(Object requestEntity) throws CreateException;

    public <T> T FindAppointmentById_XML(GenericType<T> responseType, String id) throws AppointmentNotFoundException;

    public <T> T FindAppointmentById_JSON(Class<T> responseType, String id) throws AppointmentNotFoundException;

    public void DeleteAppointment(String id) throws DeleteException;

    public <T> T FindAppointmentByDate_XML(GenericType<T> responseType, String appointmentDate) throws AppointmentNotFoundException;

    public <T> T FindAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws AppointmentNotFoundException;

}
