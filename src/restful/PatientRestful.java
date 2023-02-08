/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import exceptions.UserException;
import exceptions.ClientErrorException;
import interfaces.PatientInterface;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:PatientFacadeREST
 * [entities.patient]<br>
 * USAGE:
 * <pre>
 * PatientRestful client = new PatientRestful();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 *
 * @author 2dam
 */
public class PatientRestful implements PatientInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    public PatientRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.patient");
    }

    public void sendRecoveryEmail_XML(Object requestEntity) throws ClientErrorException {
        webTarget.path("sendRecoveryEmail").request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void sendRecoveryEmail_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.path("sendRecoveryEmail").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void createPatient_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void createPatient_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T findAllPatients_XML(GenericType<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException(e.getMessage() + "\nPatients not found. Try again later");
        }
    }

    public <T> T findAllPatients_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void removePatient(String dni) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{dni})).request().delete();
    }

    public <T> T findAllPatientsByPsychologist_XML(GenericType<T> responseType, String dniPsychologist) throws UserException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findPatientsByPsychologist/{0}", new Object[]{dniPsychologist}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new UserException(e.getMessage() + "\nPatients not found. Try again later");
        }

    }

    public <T> T findAllPatientsByPsychologist_JSON(Class<T> responseType, String dniPsychologist) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPatientsByPsychologist/{0}", new Object[]{dniPsychologist}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void editPatient_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void editPatient_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void close() {
        client.close();
    }

}
