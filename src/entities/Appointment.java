package entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Janam
 */


public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Long idAppointment;

    
    private Boolean appointmentChange;

    private Date appointmentDate;

    private Patient patient;

    private Psychologist psychologist;

    /**
     * Empty constructor
     */
    public Appointment() {
        super();
    }

    /**
     * Constructor with parameters
     *
     * @param idAppointmet
     * @param appointmentDate
     * @param appointmentChange
     * @param patient
     * @param psychologist
     */
    public Appointment(Long idAppointmet, Date appointmentDate, Boolean appointmentChange, Patient patient, Psychologist psychologist) {
        this.idAppointment = idAppointment;
        this.appointmentDate = appointmentDate;
        this.appointmentChange = appointmentChange;
        this.patient = patient;
        this.psychologist = psychologist;
    }

    //Getters & Setters
    /**
     *
     * @return idAppointment
     */
    public Long getidAppointment() {
        return idAppointment;
    }

    /**
     *
     * @param idAppointment
     */
    public void setidAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    /**
     *
     * @param appointmentDate
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    /**
     *
     * @return appointmentDate
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     *
     * @param appointmentChange
     */
    public void setAppointmentChange(Boolean appointmentChange) {
        this.appointmentChange = appointmentChange;
    }

    /**
     *
     * @return appointmentChange
     */
    public Boolean getAppointmentChange() {
        return appointmentChange;
    }

    /**
     *
     * @param patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     *
     * @return patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     *
     * @param psychologist
     */
    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }

    /**
     *
     * @return psychologist
     */
    public Psychologist getPsychologist() {
        return psychologist;
    }

    /**
     * HasCode
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAppointment != null ? idAppointment.hashCode() : 0);
        return hash;
    }

    /**
     *
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Appointment)) {
            return false;
        }
        Appointment other = (Appointment) object;
        if ((this.idAppointment == null && other.idAppointment != null) || (this.idAppointment != null && !this.idAppointment.equals(other.idAppointment))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Appointment{" + "idAppointment=" + idAppointment + ", appointmentChange=" + appointmentChange + ", appointmentDate=" + appointmentDate + ", patient=" + patient + ", psychologist=" + psychologist + '}';
    }
}
