/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.DiagnosisNotFoundException;
import exceptions.DeleteException;
import exceptions.UpdateException;
import exceptions.*;
import entities.Diagnosis;
import entities.Patient;
import java.util.Date;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author unaiz
 */
public interface DiagnosisInterface {

    public void createDiagnosis_XML(Object requestEntity) throws CreateException;

    public void createDiagnosis_JSON(Object requestEntity) throws CreateException;

    public <T> T findAllIfPatientOnTeraphy_XML(GenericType<T> responseType, String id) throws DiagnosisNotFoundException;

    public <T> T findAllIfPatientOnTeraphy_JSON(GenericType<T> responseType, String id) throws DiagnosisNotFoundException;

    public <T> T findDiagnosisById_XML(GenericType<T> responseType, String id) throws DiagnosisNotFoundException;

    public <T> T findDiagnosisById_JSON(GenericType<T> responseType, String id)  throws DiagnosisNotFoundException;

    public void updateDiagnosis_XML(Object requestEntity)  throws UpdateException;

    public void updateDiagnosis_JSON(Object requestEntity)  throws UpdateException;

    public <T> T findAllDiagnosis_XML(GenericType<T> responseType)  throws DiagnosisNotFoundException;

    public <T> T findAllDiagnosis_JSON(GenericType<T> responseType)  throws DiagnosisNotFoundException;

    public <T> T findPatientDiagnosisByDate_XML(GenericType<T> responseType, String id, String dateLow, String dateGreat)  throws DiagnosisNotFoundException;

    public <T> T findPatientDiagnosisByDate_JSON(GenericType<T> responseType, String id, String dateLow, String dateGreat)  throws DiagnosisNotFoundException;

    public void deleteDiagnosis(String id)  throws DeleteException;

    public <T> T findAllDiagnosisByPatient_XML(GenericType<T> responseType, String id)  throws DiagnosisNotFoundException;

    public <T> T findAllDiagnosisByPatient_JSON(GenericType<T> responseType, String id)  throws DiagnosisNotFoundException;

}
