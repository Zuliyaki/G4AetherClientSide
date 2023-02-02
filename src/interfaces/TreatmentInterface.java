/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.*;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface TreatmentInterface {

    public void deleteTreatment() throws ClientErrorException;
    public <T> T findAllTreatments_XML(Class<T> responseType) throws TreatmentNotFoundException;

    public <T> T findAllTreatments_JSON(Class<T> responseType) throws TreatmentNotFoundException;

    public <T> T findTreatmentByID_XML(Class<T> responseType, String treatmentId, String MedicationId, String Day, String Daytime) throws TreatmentNotFoundException;

    public <T> T findTreatmentByID_JSON(Class<T> responseType, String treatmentId, String MedicationId, String Day, String Daytime) throws TreatmentNotFoundException;

    public void createTreatment_XML(Object requestEntity) throws CreateException;

    public void createTreatment_JSON(Object requestEntity) throws CreateException;

    public void updateTreatment_XML(Object requestEntity) throws UpdateException;

    public void updateTreatment_JSON(Object requestEntity) throws UpdateException;

    public <T> T findTreatmentsByDiagnosisId_XML(GenericType<T> responseType, Long id) throws TreatmentNotFoundException;

    public <T> T findTreatmentsByDiagnosisId_JSON(Class<T> responseType, String id) throws TreatmentNotFoundException;
}
