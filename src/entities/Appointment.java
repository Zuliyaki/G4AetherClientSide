package entities;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Janam
 */
@XmlRootElement(name = "appointment")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID for the appointment
    private Long idAppointment;

    // In case of appointment change
    private Boolean appointmentChange;

    // Dated for the appointments
    private Date appointmentDate;

    // Patient DNI for the appointment
    private Patient patient;

    // Psychologist DNI for the appointment
    private Psychologist psychologist;

    /**
     * Empty constructor
     */
    public Appointment() {
        super();
    }

    public Appointment(Long idAppointment, Date appointmentDate, Boolean appointmentChange, Patient patient, Psychologist psychologist) {
        this.idAppointment = idAppointment;
        this.appointmentDate = appointmentDate;
        this.appointmentChange = appointmentChange;
        this.patient = patient;
        this.psychologist = psychologist;
    }

    //Getters & Setters for all the appointments 
    public Long getidAppointment() {
        return idAppointment;
    }

    public void setidAppointment(Long idAppointment) {
        this.idAppointment = idAppointment;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentChange(Boolean appointmentChange) {
        this.appointmentChange = appointmentChange;
    }

    public Boolean getAppointmentChange() {
        return appointmentChange;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    @Override
    public String toString() {
        return "Appointment{" + "idAppointment=" + idAppointment + ", appointmentChange=" + appointmentChange + ", appointmentDate=" + appointmentDate + ", patient=" + patient + ", psychologist=" + psychologist + '}';
    }
}
