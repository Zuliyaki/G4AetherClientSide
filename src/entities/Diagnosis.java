package entities;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.*;
import javafx.collections.ObservableSet;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zuli
 */
@XmlRootElement(name = "diagnosis")
public class Diagnosis implements Serializable {

    private static final long serialVersionUID = 1L;

    private SimpleLongProperty diagnosisId;

    private SimpleObjectProperty<Date> diagnosisDate;

    private SimpleObjectProperty<Date> lastDiagnosisChangeDate;

    private SimpleObjectProperty<Patient> patient;

    private SimpleObjectProperty<Psychologist> psychologist;

    private SimpleObjectProperty<MentalDisease> mentalDisease;
    private Set<Treatment> treatments;
    private SimpleBooleanProperty onTherapy;

    /**
     * Empty constructor
     */
    public Diagnosis() {
        this.diagnosisId = new SimpleLongProperty();
        this.diagnosisDate = new SimpleObjectProperty();
        this.diagnosisDate = new SimpleObjectProperty();
        this.lastDiagnosisChangeDate = new SimpleObjectProperty();
        this.patient = new SimpleObjectProperty();
        this.psychologist = new SimpleObjectProperty();
        this.mentalDisease = new SimpleObjectProperty();
        this.onTherapy = new SimpleBooleanProperty();
    }

    public Diagnosis(Long diagnosisId, Date diagnosisDate, Date lastDiagnosisChangeDate, Patient patient, Psychologist psychologist, MentalDisease mentalDisease, Set<Treatment> treatments, Boolean onTherapy) {
        this.diagnosisId = new SimpleLongProperty(diagnosisId);
        this.diagnosisDate = new SimpleObjectProperty(diagnosisDate);
        this.lastDiagnosisChangeDate = new SimpleObjectProperty(lastDiagnosisChangeDate);
        this.patient = new SimpleObjectProperty(patient);
        this.psychologist = new SimpleObjectProperty(psychologist);
        this.mentalDisease = new SimpleObjectProperty(mentalDisease);
        this.treatments = treatments;
        this.onTherapy = new SimpleBooleanProperty(onTherapy);
    }

    public Long getDiagnosisId() {
        return diagnosisId.get();
    }

    public void setDiagnosisId(Long diagnosisId) {
        this.diagnosisId.set(diagnosisId);
    }

    public Date getDiagnosisDate() {
        return diagnosisDate.get();
    }

    public void setDiagnosisDate(Date diagnosisDate) {
        this.diagnosisDate.set(diagnosisDate);
    }

    public Date getLastDiagnosisChangeDate() {
        return lastDiagnosisChangeDate.get();
    }

    public void setLastDiagnosisChangeDate(Date lastDiagnosisChangeDate) {
        this.lastDiagnosisChangeDate.set(lastDiagnosisChangeDate);
    }

    public Patient getPatient() {
        return patient.get();
    }

    public void setPatient(Patient patient) {
        this.patient.set(patient);
    }

    public Psychologist getPsychologist() {
        return psychologist.get();
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist.set(psychologist);
    }

    public MentalDisease getMentalDisease() {
        return mentalDisease.get();
    }

    public void setMentalDisease(MentalDisease mentalDisease) {
        this.mentalDisease.set(mentalDisease);
    }

    @XmlTransient
    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public Boolean getOnTherapy() {
        return onTherapy.get();
    }

    public void setOnTherapy(Boolean onTherapy) {
        this.onTherapy.set(true);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.diagnosisId);
        hash = 67 * hash + Objects.hashCode(this.diagnosisDate);
        hash = 67 * hash + Objects.hashCode(this.lastDiagnosisChangeDate);
        hash = 67 * hash + Objects.hashCode(this.patient);
        hash = 67 * hash + Objects.hashCode(this.psychologist);
        hash = 67 * hash + Objects.hashCode(this.mentalDisease);
        hash = 67 * hash + Objects.hashCode(this.treatments);
        hash = 67 * hash + Objects.hashCode(this.onTherapy);
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
        final Diagnosis other = (Diagnosis) obj;
        if (!Objects.equals(this.diagnosisId, other.diagnosisId)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Diagnosis{" + "diagnosisId=" + diagnosisId + ", diagnosisDate=" + diagnosisDate + ", lastDiagnosisChangeDate=" + lastDiagnosisChangeDate + ", patient=" + patient + ", psychologist=" + psychologist + ", mentalDisease=" + mentalDisease + ", treatments=" + treatments + ", onTherapy=" + onTherapy + '}';
    }

}
