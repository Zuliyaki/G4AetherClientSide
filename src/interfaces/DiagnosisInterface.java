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

    public void createDiagnosis_XML(Object requestEntity) throws ClientErrorException;

    public void createDiagnosis_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findAllIfPatientOnTeraphy_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findAllIfPatientOnTeraphy_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findDiagnosisById_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findDiagnosisById_JSON(Class<T> responseType, String id) throws ClientErrorException;

    public void updateDiagnosis_XML(Object requestEntity) throws ClientErrorException;

    public void updateDiagnosis_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findAllDiagnosis_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAllDiagnosis_JSON(Class<T> responseType) throws ClientErrorException;

    public <T> T findPatientDiagnosisByDate_XML(Class<T> responseType, String id, String dateLow, String dateGreat) throws ClientErrorException;

    public <T> T findPatientDiagnosisByDate_JSON(Class<T> responseType, String id, String dateLow, String dateGreat) throws ClientErrorException;

    public void deleteDiagnosis(String id) throws ClientErrorException;

    public <T> T findAllDiagnosisByPatient_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findAllDiagnosisByPatient_JSON(Class<T> responseType, String id) throws ClientErrorException;

}
