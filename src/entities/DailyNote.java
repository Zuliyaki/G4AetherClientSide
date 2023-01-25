package entities;

import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author unaib
 */
@XmlRootElement(name = "dailyNote")
public class DailyNote implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Auto generated daily note id
     */
    private Long id;
    /**
     * Patient that wrote the note
     */
    private Patient dnPatient;
    /**
     * Content of the note
     */
    private String dnNoteText;
    /**
     * Comment the psychologist can do about the note
     */
    private String dnNoteComment;
    /**
     * Enum for the note if was readen or not
     */
    private EnumReadedStatus dnNoteStatus;
    /**
     * Date of creation of the note
     */
    private Date dnNoteDate;
    /**
     * Date of the last time the note was edited
     */
    private Date dnNoteDateLastEdited;
    /**
     * Score of the day [1-100] depending how it was
     */
    private Double dnDayScore;
    /**
     * Lets the dnPatient choose if the psychologist can read the note
     */
    private Boolean dnNoteReadable;

    //Costructors
    /**
     * Empty constructor
     */
    public DailyNote() {
    }

    /**
     * Constructor with params
     *
     * @param patient
     * @param noteStatus
     * @param noteDate
     * @param noteDateLastEdited
     * @param dayScore
     * @param noteReadable
     */
    public DailyNote(Patient patient, EnumReadedStatus noteStatus, Date noteDate, Date noteDateLastEdited,
            Double dayScore, Boolean noteReadable) {
        this.dnPatient = patient;
        this.dnNoteStatus = noteStatus;
        this.dnNoteDate = noteDate;
        this.dnNoteDateLastEdited = noteDateLastEdited;
        this.dnDayScore = dayScore;
        this.dnNoteReadable = noteReadable;
    }

    //Getters & Setters
    /**
     * Sets the dnPatient
     *
     * @param patient
     */
    public void setPatient(Patient patient) {
        this.dnPatient = patient;
    }

    /**
     * Get the dnPatient
     *
     * @return
     */
    public Patient getPatient() {
        return dnPatient;
    }

    /**
     * Sets the note text
     *
     * @param noteText
     */
    public void setNoteText(String noteText) {
        this.dnNoteText = noteText;
    }

    /**
     * Gets the note text
     *
     * @return
     */
    public String getNoteText() {
        return dnNoteText;
    }

    /**
     * Sets the note coment
     *
     * @param noteComent
     */
    public void setNoteComent(String noteComent) {
        this.dnNoteComment = noteComent;
    }

    /**
     * Gets the note coment
     *
     * @return
     */
    public String getNoteComent() {
        return dnNoteComment;
    }

    /**
     * Sets the note status
     *
     * @param noteStatus
     */
    public void setNoteStatus(EnumReadedStatus noteStatus) {
        this.dnNoteStatus = noteStatus;
    }

    /**
     * Gets the note status
     *
     * @return
     */
    public EnumReadedStatus getNoteStatus() {
        return dnNoteStatus;
    }

    /**
     * Sets the note date
     *
     * @param noteDate
     */
    public void setNoteDate(Date noteDate) {
        this.dnNoteDate = noteDate;
    }

    /**
     * Gets the note date
     *
     * @return
     */
    public Date getNoteDate() {
        return dnNoteDate;
    }

    /**
     * Sets the last time the note was edited
     *
     * @param noteDateLastEdited
     */
    public void setNoteDateLastEdited(Date noteDateLastEdited) {
        this.dnNoteDateLastEdited = noteDateLastEdited;
    }

    /**
     * Gets the last time the note was edited
     *
     * @return
     */
    public Date getNoteDateLastEdited() {
        return dnNoteDateLastEdited;
    }

    /**
     * Sets the day score
     *
     * @param dayScore
     */
    public void setDayScore(Double dayScore) {
        this.dnDayScore = dayScore;
    }

    /**
     * Gets the day score
     *
     * @return
     */
    public Double getDayScore() {
        return dnDayScore;
    }

    /**
     * Sets if the note is readable by the psychologist
     *
     * @param noteReadable
     */
    public void setNoteReadable(Boolean noteReadable) {
        this.dnNoteReadable = noteReadable;
    }

    /**
     * Gets if the note is readable by the psychologist
     *
     * @return
     */
    public Boolean getNoteReadable() {
        return dnNoteReadable;
    }

    /**
     * Sets the note id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the note id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
        hash = 73 * hash + Objects.hashCode(this.dnNoteDate);
        return hash;
    }

    /**
     * Checks if two DailyNotes are the same
     *
     * @param obj
     * @return Return if the two objects are the same
     */
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
        final DailyNote other = (DailyNote) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dnNoteDate, other.dnNoteDate)) {
            return false;
        }
        return true;
    }

}
