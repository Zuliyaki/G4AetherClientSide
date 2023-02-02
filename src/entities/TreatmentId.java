/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;
import javafx.beans.property.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zuli
 */
@XmlRootElement(name = "treatmentId")
public class TreatmentId implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleLongProperty diagnosisId;
    private SimpleLongProperty medicationId;
    private SimpleObjectProperty<EnumDay> day;
    private SimpleObjectProperty<EnumDayTime>  dayTime;

    //CONSTRUCTOR
    public TreatmentId() {
        this.diagnosisId = new SimpleLongProperty();
        this.medicationId = new SimpleLongProperty();
        this.day = new SimpleObjectProperty();
        this.dayTime = new SimpleObjectProperty();
    }

    public TreatmentId(Long diagnosisId,Long medicationId, EnumDay day, EnumDayTime dayTime) {
        this.diagnosisId = new SimpleLongProperty(diagnosisId);
        this.medicationId = new SimpleLongProperty(medicationId);
        this.day = new SimpleObjectProperty(day);
        this.dayTime = new SimpleObjectProperty(dayTime);
    }

    //GETTERS AND SETTERS
    public Long getDiagnosisId() {
        return diagnosisId.get();
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId.set(diagnosisId);
    }

    public Long getMedicationId() {
        return medicationId.get();
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId.set(medicationId);
    }

    @XmlElement(name = "day")
    public EnumDay getDay() {
        return day.get();
    }

    public void setDay(EnumDay day) {
        this.day.set(day);
    }
     @XmlElement(name="dayTime")
    public EnumDayTime getDayTime() {
        return dayTime.get();
    }

    public void setDayTime(EnumDayTime dayTime) {
        this.dayTime.set(dayTime);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.diagnosisId);
        hash = 29 * hash + Objects.hashCode(this.medicationId);
        hash = 29 * hash + Objects.hashCode(this.day);
        hash = 29 * hash + Objects.hashCode(this.dayTime);
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
        final TreatmentId other = (TreatmentId) obj;
        if (!Objects.equals(this.diagnosisId, other.diagnosisId)) {
            return false;
        }
        if (!Objects.equals(this.medicationId, other.medicationId)) {
            return false;
        }
        if (this.day != other.day) {
            return false;
        }
        if (this.dayTime != other.dayTime) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.day.get().toString()+" || "+ this.dayTime.get().toString();
    }

}
