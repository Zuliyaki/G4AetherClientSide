/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.Patient;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author 2dam
 */
public interface PatientInterface {
     public void createPatient_XML(Object requestEntity) throws ClientErrorException ;

    public void createPatient_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findAllPatients_XML(Class<T> responseType) throws ClientErrorException;

    public <T> T findAllPatients_JSON(Class<T> responseType) throws ClientErrorException;
    public void removePatient(String dni) throws ClientErrorException;

    public <T> T findAllPatientsByPsychologist_XML(GenericType<T> responseType, String dniPsychologist) throws ClientErrorException; 

    public <T> T findAllPatientsByPsychologist_JSON(Class<T> responseType, String dniPsychologist) throws ClientErrorException;

    public void editPatient_XML(Object requestEntity) throws ClientErrorException ;
    public void editPatient_JSON(Object requestEntity) throws ClientErrorException;

    
}
