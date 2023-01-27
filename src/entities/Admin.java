package entities;

import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leire
 */

@XmlRootElement(name="admin")
public class Admin extends User {

    private Set<MentalDisease> mentalDisease;

    /**
     * Empty constructor
     */
    public Admin() {
        super();
    }

    /**
     * Costructor with parameters
     *
     * @param mentalDisease
     */
    public Admin(Set<MentalDisease> mentalDisease) {

        this.mentalDisease = mentalDisease;
    }

    //Getters & Setters
    public Set<MentalDisease> getMentalDisease() {
        return mentalDisease;
    }

    public void setMentalDisease(Set<MentalDisease> mentalDisease) {
        this.mentalDisease = mentalDisease;
    }

    @Override
    public String toString() {
        return super.getFullName();
    }

}
