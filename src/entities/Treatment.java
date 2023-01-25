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
    private SimpleObjectProperty<EnumDay> day;
    private SimpleObjectProperty<EnumDayTime> dayTime;
    private SimpleObjectProperty<EnumMedType> medicationType;
    private SimpleObjectProperty<Diagnosis> diagnosis;
    private Medication medication;

    public Treatment() {
        this.treatmentId = new SimpleObjectProperty();
        this.day = new SimpleObjectProperty();
        this.dayTime = new SimpleObjectProperty();
        this.medicationType = new SimpleObjectProperty();
        this.diagnosis = new SimpleObjectProperty();
        this.medication = new Medication();

    }
/**
 *  public Treatment(TreatmentId treatmentId, Diagnosis diagnosis, Medication medication, EnumMedType medtype, EnumDay enumDay, EnumDayTime enumDayTime) {
        this.treatmentId = new SimpleObjectProperty<TreatmentId>(treatmentId);
        this.diagnosis = new SimpleObjectProperty<Diagnosis>(diagnosis);
        this.medication = medication;
        this.dayTime = new SimpleObjectProperty<EnumDayTime>(enumDayTime);
        this.day = new SimpleObjectProperty<EnumDay>(enumDay);
        this.medicationType = new SimpleObjectProperty<EnumMedType>(medtype);

    }
 * 
 */
   


    @XmlElement(name = "treatmentId")
    public TreatmentId getTreatmentId() {
        return treatmentId.get();
    }

    
    @XmlElement(name = "day")
    public SimpleObjectProperty<EnumDay> getDay() {
        return day;
    }

    public void setDay(SimpleObjectProperty<EnumDay> day) {
        this.day = day;
    }
    @XmlElement(name = "dayTime")
    public SimpleObjectProperty<EnumDayTime> getDayTime() {
        return dayTime;
    }

 
    public void setDayTime(SimpleObjectProperty<EnumDayTime> dayTime) {   
        this.dayTime = dayTime;
    }

    @XmlElement(name = "typeOfMedication")
    public SimpleObjectProperty<EnumMedType> getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(SimpleObjectProperty<EnumMedType> medicationType) {
        this.medicationType = medicationType;
    }

    public void setTreatmentId(TreatmentId treatmentId) {
        this.treatmentId = new SimpleObjectProperty<TreatmentId>(treatmentId);
    }

    public Diagnosis getDiagnosis() {
        return diagnosis.get();
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = new SimpleObjectProperty<Diagnosis>(diagnosis);
    }

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
        hash = 47 * hash + Objects.hashCode(this.day);
        hash = 47 * hash + Objects.hashCode(this.dayTime);
        hash = 47 * hash + Objects.hashCode(this.medicationType);
        hash = 47 * hash + Objects.hashCode(this.diagnosis);
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
