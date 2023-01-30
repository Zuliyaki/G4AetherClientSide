/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.PatientInterface;
import restful.PatientRestful;

/**
 *
 * @author unaib
 */
public class PatientFactory {

    public static PatientInterface model;

    public static PatientInterface getModel() {
        if (model == null) {
            model = new PatientRestful();
        }
        return model;
    }

}
