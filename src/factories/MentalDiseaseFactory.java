/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.MentalDiseaseInterface;
import restful.MentalDiseaseRestful;

/**
 *
 * @author Leire
 */
public class MentalDiseaseFactory {

    /**
     *
     */
    public MentalDiseaseInterface data;

    public MentalDiseaseFactory() {
    }

    /**
     * Load the data variable, if it is not previously loaded
     *
     * @return data MentalDisease
     */
    public MentalDiseaseInterface getMentalDisease() {

        data = new MentalDiseaseRestful();

        return data;
    }

}
