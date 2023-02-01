/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entities.MentalDisease;
import exceptions.MentalDiseaseException;
import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Leire
 */
public interface MentalDiseaseInterface {

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public void edit_XML(Object requestEntity) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public <T> T getAllMentalDiseases_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T getMentalDiseasesById_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T getMentalDiseasesByName_XML(GenericType<T> responseType, String name) throws ClientErrorException;

    public <T> T getAllMentalDiseasesOrderByName_XML(GenericType<T> responseType) throws MentalDiseaseException;
}
