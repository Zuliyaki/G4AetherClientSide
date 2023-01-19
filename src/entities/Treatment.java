package entities;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.*;

public class Treatment implements Serializable {

    private static final long serialVersionUID = 1L;
    private SimpleObjectProperty<TreatmentId> treatmentId;
    private SimpleObjectProperty<Diagnosis> diagnosis;

    private Medication medication;

    public Treatment() {
    }

    public Treatment(TreatmentId treatmentId, Diagnosis diagnosis, Medication medication) {
        this.treatmentId = new SimpleObjectProperty<TreatmentId>(treatmentId);
        this.diagnosis = new SimpleObjectProperty<Diagnosis>(diagnosis);
        this.medication = medication;
    }

    public TreatmentId getTreatmentId() {
        return treatmentId.get();
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
        hash = 37 * hash + Objects.hashCode(this.treatmentId);
        hash = 37 * hash + Objects.hashCode(this.diagnosis);
        hash = 37 * hash + Objects.hashCode(this.medication);
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
