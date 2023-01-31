package interfaces;

import javax.ws.rs.ClientErrorException;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface AppointmentInterface {

    public <T> T FindAllAppointments_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T FindAllAppointments_JSON(Class<T> responseType) throws ClientErrorException;

    public void UpdateAppointment_XML(Object requestEntity) throws ClientErrorException;

    public void updateAppointment_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T FindAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    public <T> T FindAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    public void createAppointment_XML(Object requestEntity) throws ClientErrorException;

    public void createAppointment_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T FindAppointmentById_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T FindAppointmentById_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public void DeleteAppointment(String id) throws ClientErrorException;

    public <T> T FindAppointmentByDate_XML(GenericType<T> responseType, String appointmentDate) throws ClientErrorException;

    public <T> T FindAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws ClientErrorException;

}
