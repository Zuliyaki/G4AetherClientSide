/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import exceptions.*;
import interfaces.TreatmentInterface;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:TreatmentFacadeREST
 * [entities.treatment]<br>
 * USAGE:
 * <pre>
 *        tuki client = new tuki();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class TreatmentResful implements TreatmentInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/G4Aether/webresources";

    public TreatmentResful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.treatment");
    }

    @Override
    public void deleteTreatment() throws ClientErrorException {
        webTarget.request().delete();
    }

    @Override
    public <T> T findAllTreatments_XML(Class<T> responseType) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    @Override
    public <T> T findAllTreatments_JSON(Class<T> responseType) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    @Override
    public <T> T findTreatmentByID_XML(Class<T> responseType, String treatmentId, String MedicationId, String Day, String Daytime) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("get/{0}/{1}/{2}/{3}", new Object[]{treatmentId, MedicationId, Day, Daytime}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    @Override
    public <T> T findTreatmentByID_JSON(Class<T> responseType, String treatmentId, String MedicationId, String Day, String Daytime) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("get/{0}/{1}/{2}/{3}", new Object[]{treatmentId, MedicationId, Day, Daytime}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    @Override
    public void createTreatment_XML(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));

        } catch (Exception e) {
            throw new CreateException("treatment cannot be created ");
        }
    }

    @Override
    public void createTreatment_JSON(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            throw new CreateException("treatment cannot be created ");
        }
    }

    @Override
    public void updateTreatment_XML(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));

        } catch (Exception e) {
            throw new UpdateException("treatment cannot be updated ");
        }

    }

    @Override
    public void updateTreatment_JSON(Object requestEntity) throws UpdateException {

        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            throw new UpdateException("treatment cannot be updated ");
        }
    }

    @Override
    public <T> T findTreatmentsByDiagnosisId_XML(GenericType<T> responseType, Long id) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("diagnosis/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    @Override
    public <T> T findTreatmentsByDiagnosisId_JSON(Class<T> responseType, String id) throws TreatmentNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("diagnosis/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new TreatmentNotFoundException("treatment not found");
        }

    }

    public void close() {
        client.close();
    }

}
