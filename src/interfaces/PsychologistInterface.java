package interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Janam
 */
public interface PsychologistInterface {

    public void createPsychologist_XML(Object requestEntity) throws ClientErrorException;

    public void createPsychologist_JSON(Object requestEntity) throws ClientErrorException;

    public void removePsychologist(String dni) throws ClientErrorException;

    public <T> T findAllPsychologists_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAllPsychologists_JSON(Class<T> responseType) throws ClientErrorException;

    public void editPsychologist_XML(Object requestEntity) throws ClientErrorException;

    public void editPsychologist_JSON(Object requestEntity) throws ClientErrorException;

}
