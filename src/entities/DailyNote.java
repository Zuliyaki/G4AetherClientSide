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

    private Long id;

    private Patient dnPatient;

    private String dnNoteText;

    private String dnNoteComment;

    private EnumReadedStatus dnNoteStatus;

    private Date dnNoteDate;

    private Date dnNoteDateLastEdited;

    private Double dnDayScore;
    private Boolean dnNoteReadable;

    //Costructors
    public DailyNote() {
    }

    public DailyNote(Patient patient, EnumReadedStatus noteStatus, Date noteDate, Date noteDateLastEdited,
            Double dayScore, Boolean noteReadable) {
        this.dnPatient = patient;
        this.dnNoteStatus = noteStatus;
        this.dnNoteDate = noteDate;
        this.dnNoteDateLastEdited = noteDateLastEdited;
        this.dnDayScore = dayScore;
        this.dnNoteReadable = noteReadable;
    }

    public void setPatient(Patient patient) {
        this.dnPatient = patient;
    }

    public Patient getPatient() {
        return dnPatient;
    }

    public void setNoteText(String noteText) {
        this.dnNoteText = noteText;
    }

    public String getNoteText() {
        return dnNoteText;
    }

    public void setNoteComent(String noteComent) {
        this.dnNoteComment = noteComent;
    }

    public String getNoteComent() {
        return dnNoteComment;
    }

    public void setNoteStatus(EnumReadedStatus noteStatus) {
        this.dnNoteStatus = noteStatus;
    }

    public EnumReadedStatus getNoteStatus() {
        return dnNoteStatus;
    }

    public void setNoteDate(Date noteDate) {
        this.dnNoteDate = noteDate;
    }

    public Date getNoteDate() {
        return dnNoteDate;
    }

    public void setNoteDateLastEdited(Date noteDateLastEdited) {
        this.dnNoteDateLastEdited = noteDateLastEdited;
    }

    public Date getNoteDateLastEdited() {
        return dnNoteDateLastEdited;
    }

    public void setDayScore(Double dayScore) {
        this.dnDayScore = dayScore;
    }

    public Double getDayScore() {
        return dnDayScore;
    }

    public void setNoteReadable(Boolean noteReadable) {
        this.dnNoteReadable = noteReadable;
    }

    public Boolean getNoteReadable() {
        return dnNoteReadable;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
