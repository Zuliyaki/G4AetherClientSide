package entities;

import java.io.Serializable;
import java.util.Set;
import javafx.beans.property.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zuli
 */


public class Medication implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private SimpleLongProperty medicationId;
    private SimpleStringProperty medicationName;
    private SimpleStringProperty description;

    private SimpleObjectProperty<EnumMedType> typeOfMedication;
    private Set<Treatment> treatments;

    /**
     * Empty constructor
     */
    public Medication() {
        super();
    }

    public Medication(Long medicationId, String medicationName, String description, EnumMedType typeOfMedication, Set<Treatment> treatments) {
        this.medicationId =  new SimpleLongProperty(medicationId);
        this.medicationName = new SimpleStringProperty(medicationName);
        this.description = new SimpleStringProperty(description);
        this.typeOfMedication = new SimpleObjectProperty(typeOfMedication);
        this.treatments = treatments;
    }
    //Getters & Setters

    public Long getMedicationId() {
        return medicationId.get();
    }

    public String getMedicationName() {
        return medicationName.get();
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = new SimpleStringProperty(medicationName);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }
    @XmlElement(name="typeOfMedication")
    public String getTypeOfMedication() {
        return typeOfMedication.toString();
    }

    public void setTypeOfMedication(EnumMedType typeOfMedication) {
        this.typeOfMedication  = new SimpleObjectProperty(typeOfMedication);
    }

    @XmlTransient
    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    /**
     * 
     * @return @Override
    }
     */
    
    /**
     * 
     *   @Override
    public String toString() {
        return this.getMedicationName();
    }
 
     */
  
    
    
}
