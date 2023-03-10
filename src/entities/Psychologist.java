package entities;

import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author unaibAndLeire
 */
@XmlRootElement
public class Psychologist extends User {

    private String titulation;

    private String invitationCode;

    private Set<Patient> patient;

    private Set<Appointment> appointments;

    /**
     * Empty constructor
     */
    public Psychologist() {
        super();
    }

    //OnlyPyschologist constructor
    public Psychologist(String titulation, String invitationCode, Set<Patient> patient, Set<Appointment> appointments) {
        this.titulation = titulation;
        this.invitationCode = invitationCode;
        this.patient = patient;
        this.appointments = appointments;
    }

    //Pshychologist with super constructor
    public Psychologist(String titulation, String invitationCode, Set<Patient> patient, Set<Appointment> appointments, String dni, String fullName, Date birthDate, String password, Integer phoneNumber, String email) {
        super(dni, fullName, birthDate, password, phoneNumber, email);
        this.titulation = titulation;
        this.invitationCode = invitationCode;
        this.patient = patient;
        this.appointments = appointments;
    }

    //Getters & Setters
    public String getTitulation() {
        return titulation;
    }

    public void setTitulation(String titulation) {
        this.titulation = titulation;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    @XmlTransient
    public Set<Patient> getPatient() {
        return patient;
    }

    public void setPatient(Set<Patient> patient) {
        this.patient = patient;
    }

    @XmlTransient
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return super.getDni();
    }

}
