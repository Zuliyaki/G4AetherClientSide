/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entities.Diagnosis;
import exceptions.*;
import interfaces.DiagnosisInterface;
import java.util.List;
import java.util.ResourceBundle;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:DiagnosisFacadeREST
 * [entities.diagnosis]<br>
 * USAGE:
 * <pre>
 * DiagnosisRestful client = new DiagnosisRestful();
 * Object response = client.XXX(...);
 * // do whatever with response
 * client.close();
 * </pre>
 *
 * @author 2dam
 */
public class DiagnosisRestful implements DiagnosisInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    public DiagnosisRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.diagnosis");
    }

    @Override
    public void createDiagnosis_XML(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));

        } catch (Exception e) {
            throw new CreateException("error creating diagnosis");
        }
    }

    @Override
    public void createDiagnosis_JSON(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            throw new CreateException("error creating diagnosis");
        }
    }

    @Override
    public <T> T findAllIfPatientOnTeraphy_XML(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("onteraphy/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findAllIfPatientOnTeraphy_JSON(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("onteraphy/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findDiagnosisById_XML(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("diagnosisbyID/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findDiagnosisById_JSON(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("diagnosisbyID/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public void updateDiagnosis_XML(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));

        } catch (Exception e) {
            throw new UpdateException("cannot update the diagnosis");
        }

    }

    @Override
    public void updateDiagnosis_JSON(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));

        } catch (Exception e) {
            throw new UpdateException("cannot update the diagnosis");
        }
    }

    @Override
    public <T> T findAllDiagnosis_XML(GenericType<T> responseType) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findAllDiagnosis_JSON(GenericType<T> responseType) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findPatientDiagnosisByDate_XML(GenericType<T> responseType, String id, String dateLow, String dateGreat) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findDiagnosisByPatientIdbeetweenDates/{0}/{1}/{2}", new Object[]{id, dateLow, dateGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findPatientDiagnosisByDate_JSON(GenericType<T> responseType, String id, String dateLow, String dateGreat) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findDiagnosisByPatientIdbeetweenDates/{0}/{1}/{2}", new Object[]{id, dateLow, dateGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public void deleteDiagnosis(String id) throws DeleteException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
        } catch (Exception e) {
            throw new DeleteException("cannot delete diagnosis");
        }

    }

    @Override
    public <T> T findAllDiagnosisByPatient_XML(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("patients/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    @Override
    public <T> T findAllDiagnosisByPatient_JSON(GenericType<T> responseType, String id) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("patients/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }

    public void close() {
        client.close();
    }

}
