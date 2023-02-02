/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.MentalDiseaseInterface;
import interfaces.PatientInterface;
import restful.MentalDiseaseRestful;
import restful.PatientRestful;

/**
 *
 * @author Leire
 */
public class MentalDiseaseFactory {

    /**
     *
     */
     public static MentalDiseaseInterface model;

    public static MentalDiseaseInterface getModel() {
        if (model == null) {
            model = new MentalDiseaseRestful();
        }
        return model;
    }
}
