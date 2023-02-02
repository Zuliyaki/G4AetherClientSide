package restful;

import exceptions.AppointmentNotFoundException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.UpdateException;
import interfaces.AppointmentInterface;
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
 * @author Janam
 */
public class AppointmentRestful implements AppointmentInterface {

    private final WebTarget webTarget;
    private final Client client;
    private static final String BASE_URI = "http://localhost:8080/G4Aether/webresources";

    public AppointmentRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.appointment");
    }

    @Override
    public <T> T FindAllAppointments_XML(GenericType<T> responseType) throws AppointmentNotFoundException {

        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }
    }

    @Override
    public <T> T FindAllAppointments_JSON(Class<T> responseType) throws AppointmentNotFoundException {

        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public void UpdateAppointment_XML(Object requestEntity) throws UpdateException {

        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        } catch (Exception e) {
            throw new UpdateException("Error Updating Appointment");
        }

    }

    @Override
    public void updateAppointment_JSON(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new UpdateException("Error Updating Appointment");
        }

    }

    @Override
    public <T> T FindAppointmentByChange_XML(Class<T> responseType, String appointmentchange) throws AppointmentNotFoundException {

        try {

            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("getAppointmentByChange/{0}", new Object[]{appointmentchange}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public <T> T FindAppointmentByChange_JSON(Class<T> responseType, String appointmentchange) throws AppointmentNotFoundException {

        try {

            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("getAppointmentByChange/{0}", new Object[]{appointmentchange}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public void createAppointment_XML(Object requestEntity) throws CreateException {

        try {

            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
        } catch (Exception e) {
            throw new CreateException("Error Updating Appointment");
        }

    }

    @Override
    public void createAppointment_JSON(Object requestEntity) throws CreateException {

        try {

            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new CreateException("Error Updating Appointment");
        }

    }

    @Override
    public <T> T FindAppointmentById_XML(GenericType<T> responseType, String id) throws AppointmentNotFoundException {

        try {

            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public <T> T FindAppointmentById_JSON(Class<T> responseType, String id) throws AppointmentNotFoundException {

        try {

            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public void DeleteAppointment(String id) throws DeleteException {

        try {

            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
        } catch (Exception e) {
            throw new DeleteException("Appointment not found");
        }

    }

    @Override
    public <T> T FindAppointmentByDate_XML(GenericType<T> responseType, String appointmentDate) throws AppointmentNotFoundException {

        try {

            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("getAppointmentByDate/{0}", new Object[]{appointmentDate}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    @Override
    public <T> T FindAppointmentByDate_JSON(Class<T> responseType, String appointmentDate) throws AppointmentNotFoundException {

        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("getAppointmentByDate/{0}", new Object[]{appointmentDate}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new AppointmentNotFoundException("Appointment not found");
        }

    }

    public void close() {
        client.close();
    }

}
