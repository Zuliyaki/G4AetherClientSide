package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface PatientInterface {

    public <T> T findAllPatients_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAllPatientsByPsychologist_XML(GenericType<T> responseType, String dniPsychologist) throws ClientErrorException;
}
