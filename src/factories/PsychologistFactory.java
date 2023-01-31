package factories;

import interfaces.PsychologistInterface;
import restful.PsychologistRestful;

/**
 *
 * @author Janam
 */
public class PsychologistFactory {

    public static PsychologistInterface model;

    public static PsychologistInterface getModel() {
        
        if (model == null) {
            
            model = (PsychologistInterface) new PsychologistRestful();
        }
        return model;
    }
}
