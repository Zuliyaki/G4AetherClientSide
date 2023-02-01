package factories;

import interfaces.AppointmentInterface;
import restful.AppointmentRestful;

/**
 * 
 * @author Janam
 */
public class AppointmentFactory {

    public static AppointmentInterface model;

    /**
     * 
     * @return model
     */
    public static AppointmentInterface getModel() {
        
        if (model == null) {
            
            model = new AppointmentRestful();
        }
        return model;
    }
}
