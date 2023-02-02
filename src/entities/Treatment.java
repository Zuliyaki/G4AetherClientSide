package entities;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "treatment")
public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleObjectProperty<TreatmentId> treatmentId;
    private Medication medication;

    public Treatment() {
        this.treatmentId = new SimpleObjectProperty();
        this.medication = new Medication();
       
    }

    public Treatment(TreatmentId treatmentId, Medication medication) {
        this.treatmentId = new SimpleObjectProperty<>(treatmentId);
        this.medication = medication;
    }

     @XmlElement(name = "treatmentId")
    public TreatmentId getTreatmentId() {
        return treatmentId.getValue();
    }

    public void setTreatmentId(TreatmentId treatmentId) {
        this.treatmentId.set(treatmentId);
    }

    @XmlElement(name = "medication")
    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.treatmentId);
        hash = 47 * hash + Objects.hashCode(this.medication);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Treatment other = (Treatment) obj;
        if (!Objects.equals(this.treatmentId, other.treatmentId)) {
            return false;
        }
        return true;
    }

}
