package interfaces;

import exceptions.PatientException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface PatientInterface {

    public <T> T findAllPatients_XML(GenericType<T> responseType) throws PatientException;

    public <T> T findAllPatientsByPsychologist_XML(GenericType<T> responseType, String dniPsychologist) throws PatientException;
}
