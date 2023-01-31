package factories;

import interfaces.PatientInterface;
import restful.PatientRestful;

/**
 *
 * @author Janam
 */
public class PatientFactory {

    public static PatientInterface model;

    public static PatientInterface getModel() {
        
        if (model == null) {
            
            model = (PatientInterface) new PatientRestful();
        }
        
        return model;
    }
}
