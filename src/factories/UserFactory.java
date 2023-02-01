/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.UserInterface;
import restful.UserRestful;

/**
 *
 * @author unaib
 */
public class UserFactory {

    public static UserInterface model;

    public static UserInterface getModel() {
        if (model == null) {
            model = new UserRestful();
        }
        return model;
    }

}
