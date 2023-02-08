/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import exceptions.*;
import entities.DailyNote;
import interfaces.DailyNotesInterface;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:DailyNoteFacadeREST
 * [entities.dailynote]<br>
 * USAGE:
 * <pre>
 *        DailyNoteRestful client = new DailyNoteRestful();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author unaib
 */
public class DailyNoteRestful implements DailyNotesInterface {

    private WebTarget webTarget;
    private Client client;
    private final ResourceBundle configFile = ResourceBundle.getBundle("config.config");
    private final String BASE_URI = configFile.getString("BASE_URI");

    public DailyNoteRestful() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.dailynote");
    }

    public <T> T findPatientNotesBetweenDayScores_XML(GenericType<T> responseType, String patientId, Double dayScoreLow, Double dayScoreGreat) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findBetweenScores/{0}/{1}/{2}", new Object[]{patientId, dayScoreLow, dayScoreGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientNotesBetweenDayScores_JSON(Class<T> responseType, String patientId, Double dayScoreLow, Double dayScoreGreat) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findBetweenScores/{0}/{1}/{2}", new Object[]{patientId, dayScoreLow, dayScoreGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findAllDailyNotes_XML(GenericType<T> responseType) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findAllDailyNotes_JSON(Class<T> responseType) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public void edit_XML(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), DailyNote.class);
        } catch (Exception e) {
            throw new UpdateException(e.getMessage() + "\nError modifying the daily note. Try again later");
        }
    }

    public void edit_JSON(Object requestEntity) throws UpdateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new UpdateException(e.getMessage() + "\nError modifying the daily note. Try again later");
        }
    }

    public <T> T findPatientEditedDailyNotes_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findEditedNotes/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientEditedDailyNotes_JSON(Class<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findEditedNotes/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientDailyNotesByNotReadable_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findNotReadableNotes/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientDailyNotesByNotReadable_JSON(Class<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findNotReadableNotes/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findDailyNoteById_XML(GenericType<T> responseType, String id) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findDailyNoteById_JSON(Class<T> responseType, String id) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public void create_XML(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), DailyNote.class);
        } catch (Exception e) {
            throw new CreateException(e.getMessage() + "\nError creating the daily note. Try again later");
        }
    }

    public void create_JSON(Object requestEntity) throws CreateException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            throw new CreateException(e.getMessage() + "\nError creating the daily note. Try again later");
        }
    }

    public <T> T findPatientDailyNotesBetweenDates_XML(GenericType<T> responseType, String patientId, String dateLow, String dateGreat) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findBetweenDates/{0}/{1}/{2}", new Object[]{patientId, dateLow, dateGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientDailyNotesBetweenDates_JSON(Class<T> responseType, String patientId, String dateLow, String dateGreat) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findBetweenDates/{0}/{1}/{2}", new Object[]{patientId, dateLow, dateGreat}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientDailyNoteByDate_XML(GenericType<T> responseType, String patientId, String date) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByDate/{0}/{1}", new Object[]{patientId, date}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findPatientDailyNoteByDate_JSON(Class<T> responseType, String patientId, String date) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByDate/{0}/{1}", new Object[]{patientId, date}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public void remove(String id) throws DeleteException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(DailyNote.class);
        } catch (Exception e) {
            throw new DeleteException(e.getMessage() + "\nError deleting the daily note. Try again later.");
        }
    }

    @Override
    public <T> T findAllDailyNotesByPatientId_XML(GenericType<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByPatient/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public <T> T findAllDailyNotesByPatientId_JSON(Class<T> responseType, String patientId) throws DailyNoteNotFoundException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByPatient/{0}", new Object[]{patientId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
        } catch (Exception e) {
            throw new DailyNoteNotFoundException(e.getMessage() + "\nDaily Notes not found. Try again later");
        }
    }

    public void close() {
        client.close();
    }

}
