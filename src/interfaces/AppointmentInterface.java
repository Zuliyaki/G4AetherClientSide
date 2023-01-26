package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface AppointmentInterface {

    public <T> T getAllAppointments_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T getAllAppointments_JSON(Class<T> responseType) throws ClientErrorException;

    public void edit_XML(Object requestEntity) throws ClientErrorException;

    public void edit_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T getAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    public <T> T getAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void create_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T getAppointmentById_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T getAppointmentById_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public <T> T getAppointmentByDate_XML(Class<T> responseType, String appointmentDate) throws ClientErrorException;

    public <T> T getAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws ClientErrorException;
}
