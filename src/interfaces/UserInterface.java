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
public interface UserInterface {

    public void edit_XML(Object requestEntity) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void remove(String dni) throws ClientErrorException;

    public <T> T findUserByDni_XML(GenericType<T> responseType, String dni) throws ClientErrorException;

    public <T> T findAllUsers_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T logInUser_XML(Class<T> responseType, String dniUser, String passwordUser) throws ClientErrorException;
}
