/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.*;
import restful.*;

/**
 *
 * @author unaiz
 */
public class DiagnosisFactory {

    public static DiagnosisInterface model;

    public static DiagnosisInterface getModel() {
        if (model == null) {
            model = new DiagnosisRestful();
        }
        return model;
    }

}