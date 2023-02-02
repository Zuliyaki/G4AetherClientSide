package interfaces;

import exceptions.PsychologistException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface PsychologistInterface {

    public void removePsychologist(String dni) throws PsychologistException;

    public <T> T findAllPsychologists_XML(GenericType<T> responseType) throws PsychologistException;
}
