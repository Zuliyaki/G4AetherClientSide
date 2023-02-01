/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author unaib
 */
public interface PatientInterface {
    
    public void createPatient_XML(Object requestEntity) throws ClientErrorException;
    
    public void sendRecoveryEmail_XML(Object requestEntity) throws ClientErrorException;

    public <T> T findAllPatients_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAllPatientsByPsychologist_XML(GenericType<T> responseType, String dniPsychologist) throws ClientErrorException;
    
}
