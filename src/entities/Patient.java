package entities;

import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author unaibAndLeire
 */
@XmlRootElement(name="patient")
public class Patient extends User {

    private String mbti;

    private Set<DailyNote> dailyNotes;

    private Set<Diagnosis> diagnosises;

    private Psychologist psychologist;

    private Set<Appointment> appointments;

    public Patient() {
        super();
    }

    public Patient(String mbti, Set<DailyNote> dailyNotes, Set<Diagnosis> diagnosises, Psychologist psychologist, Set<Appointment> appointments) {
        this.mbti = mbti;
        this.dailyNotes = dailyNotes;
        this.diagnosises = diagnosises;
        this.psychologist = psychologist;
        this.appointments = appointments;
    }

    public Patient(String mbti, Set<DailyNote> dailyNotes, Set<Diagnosis> diagnosises, Psychologist psychologist, Set<Appointment> appointments, String dni, String fullName, Date birthDate, String password, Integer phoneNumber, String email) {
        super(dni, fullName, birthDate, password, phoneNumber, email);
        this.mbti = mbti;
        this.dailyNotes = dailyNotes;
        this.diagnosises = diagnosises;
        this.psychologist = psychologist;
        this.appointments = appointments;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    @XmlTransient
    public Set<DailyNote> getDailyNotes() {
        return dailyNotes;
    }

    public void setDailyNotes(Set<DailyNote> dailyNotes) {
        this.dailyNotes = dailyNotes;
    }

    @XmlTransient
    public Set<Diagnosis> getDiagnosises() {
        return diagnosises;
    }

    public void setDiagnosises(Set<Diagnosis> diagnosises) {
        this.diagnosises = diagnosises;
    }

    public Psychologist getPsychologist() {
        return psychologist;
    }

    public void setPsychologist(Psychologist psychologist) {
        this.psychologist = psychologist;
    }

    @XmlTransient
    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }
}
