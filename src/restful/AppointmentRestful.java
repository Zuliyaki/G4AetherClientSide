package restful;

import interfaces.AppointmentInterface;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:AppointmentFacadeREST
 * [entities.appointment]<br>
 * USAGE:
 * <pre>
 *        AppointmentRestful client = new AppointmentRestful();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 *
 * This class implements {@link AppointmentInterface} business logic interface
 * using a RESTful web client to access bussines logic in an Java EE application
 * server.
 *
 * @author Janam
 */
public class AppointmentRestful implements AppointmentInterface {

    //REST appointment web client
    private final WebTarget webTarget;
    private final Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    /**
     * Create a AppointmentRestful object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public AppointmentRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.appointment");
    }

    /**
     * XML This method returns a Collection of {@link Appointment}, containing
     * all appointments data.
     *
     * @param <T>
     * @param responseType
     * @return Collection The collection with all {@link Appointment} data for
     * users.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAllAppointments_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * JSON This method returns a Collection of {@link Appointment}, containing
     * all appointments data.
     *
     * @param <T>
     * @param responseType
     * @return Collection The collection with all {@link Appointment} data for
     * users.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAllAppointments_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * XML This method updates data for an existing Appointment data for user.
     * This is done by sending a PUT request to a RESTful web service.
     *
     * @param requestEntity The Appointment object to be updated.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public void UpdateAppointment_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * JSON This method updates data for an existing Appointment data for user.
     * This is done by sending a PUT request to a RESTful web service.
     *
     * @param requestEntity The Appointment object to be updated.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public void updateAppointment_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * XML
     *
     * @param <T>
     * @param responseType
     * @param appointmentchange
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getAppointmentByChange/{0}", new Object[]{appointmentchange}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * JSON
     *
     * @param <T>
     * @param responseType
     * @param appointmentchange
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getAppointmentByChange/{0}", new Object[]{appointmentchange}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * XML This method adds a new created Appointment. This is done by sending a
     * POST request to a RESTful web service.
     *
     * @param requestEntity The Appointment object to be added.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public void createAppointment_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * JSON This method adds a new created Appointment. This is done by sending
     * a POST request to a RESTful web service.
     *
     * @param requestEntity The Appointment object to be added.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public void createAppointment_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * XML
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentById_XML(GenericType<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * JSON
     *
     * @param <T>
     * @param responseType
     * @param id
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * This method deletes data for an existing appointment. This is done by
     * sending a DELETE request to a RESTful web service.
     *
     * @param id The Appointment object to be deleted.
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public void DeleteAppointment(String id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    /**
     * XML
     *
     * @param <T>
     * @param responseType
     * @param appointmentDate
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentByDate_XML(GenericType<T> responseType, String appointmentDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getAppointmentByDate/{0}", new Object[]{appointmentDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * JSON
     *
     * @param <T>
     * @param responseType
     * @param appointmentDate
     * @return
     * @throws ClientErrorException If there is any error while processing.
     */
    @Override
    public <T> T FindAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("getAppointmentByDate/{0}", new Object[]{appointmentDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Close client
     */
    public void close() {
        client.close();
    }

}
