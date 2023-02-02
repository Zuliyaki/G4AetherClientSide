/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.*;
import restful.TreatmentResful;

/**
 *
 * @author unaiz
 */
public class TreatmentFactory {

    public static TreatmentInterface model;

    public static TreatmentInterface getModel() {
        if (model == null) {
            model = new TreatmentResful();
        }
        return model;
    }

}