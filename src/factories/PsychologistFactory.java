/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.PsychologistInterface;
import restful.PsychologistRestful;

/**
 *
 * @author unaib
 */
public class PsychologistFactory {

    public static PsychologistInterface model;

    public static PsychologistInterface getModel() {
        if (model == null) {
            model = new PsychologistRestful();
        }
        return model;
    }

}
