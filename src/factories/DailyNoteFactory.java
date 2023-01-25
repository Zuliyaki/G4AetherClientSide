/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.DailyNotesInterface;
import restful.DailyNoteRestful;

/**
 *
 * @author unaib
 */
public class DailyNoteFactory {

    public static DailyNotesInterface model;

    public static DailyNotesInterface getModel() {
        if (model == null) {
            model = new DailyNoteRestful();
        }
        return model;
    }

}
