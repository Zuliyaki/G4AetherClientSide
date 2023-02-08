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
 * @author unaiZ
 */
public class DiagnosisRestful implements DiagnosisInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    /**
     * the resfut init
     */
    public DiagnosisRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.diagnosis");
    }
/**
 * create a diagnosis on the database
 * @param requestEntity the diagnosis to be created
 * @throws CreateException CreateException
 */
    @Override
    public void createDiagnosis_XML(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),Diagnosis.class);

        } catch (Exception e) {
            throw new CreateException("error creating diagnosis");
        }
    }
/**
 * create a diagnosis on the database
 * @param requestEntity the diagnosis to be created
 * @throws CreateException CreateException
 */
    @Override
    public void createDiagnosis_JSON(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON),Diagnosis.class);

        } catch (Exception e) {
            throw new CreateException("error creating diagnosis");
        }
    }
/**
 * finds all patient that are on therapy
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the patient
 * @return the list
 * @throws DiagnosisNotFoundException DiagnosisNotFoundException
 */
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
/**
 * finds all patient that are on therapy
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the patient
 * @return the list
 * @throws DiagnosisNotFoundException DiagnosisNotFoundException
 */
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
/**
 * finds the diagnosis by id
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the diagnosis to be search
 * @return the list
 * @throws DiagnosisNotFoundException DiagnosisNotFoundException
 */
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
/**
 * finds the diagnosis by id
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the diagnosis to be search
 * @return the list
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
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
/**
 * updates the param sent diagnosis
 * @param requestEntity the diagnosis to be updated
 * @throws UpdateException  UpdateException
 */
    @Override
    public void updateDiagnosis_XML(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML),Diagnosis.class);

        } catch (Exception e) {
            throw new UpdateException(e.getMessage());
        }

    }
/**
 * updates the param sent diagnosis
 * @param requestEntity the diagnosis to be updated
 * @throws UpdateException  UpdateException
 */
    @Override
    public void updateDiagnosis_JSON(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON),Diagnosis.class);

        } catch (Exception e) {
            throw new UpdateException("cannot update the diagnosis");
        }
    }
/**
 * find all the diagnosis
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @return the list
 * @throws DiagnosisNotFoundException DiagnosisNotFoundException 
 */
    @Override
    public <T> T findAllDiagnosis_XML(GenericType<T> responseType) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }
/**
 * finds all the diagnosis
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @return the list
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
    @Override
    public <T> T findAllDiagnosis_JSON(GenericType<T> responseType) throws DiagnosisNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DiagnosisNotFoundException("diagnosis not found");
        }

    }
/**
 * finds the diagnosis by the requested dates
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the patient
 * @param dateLow the date of start
 * @param dateGreat the date of finish
 * @return the list
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
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
/**
 * finds the diagnosis by the requested dates
 * @param <T> return the list
 * @param responseType the response type that you want to be returned
 * @param id the id of the patient
 * @param dateLow the date of start
 * @param dateGreat the date of finish
 * @return the list
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
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
/**
 * delete the sended diagnosis
 * @param id the id of the diagnosis to be deleted
 * @throws DeleteException  DeleteException
 */
    @Override
    public void deleteDiagnosis(String id) throws DeleteException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Diagnosis.class);
        } catch (Exception e) {
            throw new DeleteException( e.getMessage() + "cannot delete diagnosis");
        }

    }
/**
 *  finds all diagnosis from a patient
 * @param <T> the list to be returned
 * @param responseType the type of response
 * @param id id
 * @return the list of diagnosis
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
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
/**
 *  finds all diagnosis from a patient
 * @param <T> the list to be returned
 * @param responseType the type of response
 * @param id id
 * @return the list of diagnosis
 * @throws DiagnosisNotFoundException  DiagnosisNotFoundException
 */
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
    /**
     * close the resful
     */
    public void close() {
        client.close();
    }

}
