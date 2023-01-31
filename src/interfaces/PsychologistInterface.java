package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface PsychologistInterface {

    public void removePsychologist(String dni) throws ClientErrorException;

    public <T> T findAllPsychologists_XML(GenericType<T> responseType) throws ClientErrorException;
}
