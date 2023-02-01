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
public interface PsychologistInterface {
    
    public void sendRecoveryEmail_XML(Object requestEntity) throws ClientErrorException;

    //public <T> T findPsychologistsByEmail_XML(GenericType<T> responseType) throws ClientErrorException;

    public void createPsychologist_XML(Object requestEntity) throws ClientErrorException;

    public void removePsychologist(String dni) throws ClientErrorException;

    public <T> T findAllPsychologists_XML(GenericType<T> responseType) throws ClientErrorException;

    public void editPsychologist_XML(Object requestEntity) throws ClientErrorException;
    
}
