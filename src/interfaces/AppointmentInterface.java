package interfaces;

import javax.ws.rs.ClientErrorException;

import javax.ws.rs.core.GenericType;

/**
 * Business logic interface encapsulating business methods for appointments management.
 * @author Janam
 */
public interface AppointmentInterface {

    /**
     * This method returns a Collection of Appointment, containing all appointments data in XML.
     * @param <T> s
     * @param responseType s
     * @return Collection The collection with all Appointment data for appointments. 
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAllAppointments_XML(GenericType<T> responseType) throws ClientErrorException;

    /**
     * This method returns a Collection of Appointment, containing all appointments data in JSON
     * @param <T> d
     * @param responseType d
     * @return Collection The collection with all Appointment data for appointments. 
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAllAppointments_JSON(Class<T> responseType) throws ClientErrorException;

    /**
     * XML Format Data
     * This method updates data for an existing Appointment data for user.
     * @param requestEntity  s
     * @throws ClientErrorException If there is any error while processing.
     */
    public void UpdateAppointment_XML(Object requestEntity) throws ClientErrorException;

    /**
     * JSON Format
     * This method updates data for an existing Appointment data for user.
     * @param requestEntity s
     * @throws ClientErrorException If there is any error while processing.
     */
    public void updateAppointment_JSON(Object requestEntity) throws ClientErrorException;

    /**
     * XML
     * @param <T> s
     * @param responseType d
     * @param appointmentchange d
     * @return d
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    /**
     * JSON
     * @param <T> s
     * @param responseType s
     * @param appointmentchange s
     * @return s
     * @throws ClientErrorException  s
     */
    public <T> T FindAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    /**
     * XML
     * This method adds a new created Appointment.
     * Appointment object to be added.
     * @param requestEntity s
     * @throws ClientErrorException If there is any error while processing.
     */
    public void createAppointment_XML(Object requestEntity) throws ClientErrorException;

    /**
     * JSON
     * This method adds a new created Appointment.
     * @param requestEntity Appointment object to be added.
     * @throws ClientErrorException If there is any error while processing.
     */
    public void createAppointment_JSON(Object requestEntity) throws ClientErrorException;

    /**
     * XML
     * @param <T> s
     * @param responseType s
     * @param id s
     * @return s
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAppointmentById_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    /**
     * JSON
     * @param <T> s
     * @param responseType s
     * @param id s
     * @return s
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAppointmentById_JSON(Class<T> responseType, String id) throws ClientErrorException;

    /**
     * This method deletes data for an existing appointment. 
     * @param id  Appointment object to be deleted.
     * @throws ClientErrorException If there is any error while processing.
     */
    public void DeleteAppointment(String id) throws ClientErrorException;

    /**
     * XML
     * @param <T> s
     * @param responseType s
     * @param appointmentDate s
     * @return s
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAppointmentByDate_XML(GenericType<T> responseType, String appointmentDate) throws ClientErrorException;

    /**
     * JSON
     * @param <T> s
     * @param responseType s
     * @param appointmentDate s
     * @return s
     * @throws ClientErrorException If there is any error while processing.
     */
    public <T> T FindAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws ClientErrorException;

}
