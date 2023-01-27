package entities;

import java.io.Serializable;

import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="mentalDisease")
public class MentalDisease implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idMentalDisease;

    private Admin mdadmin;

    private EnumMentalDisease mdType;

    private String mdName;

    private String mdDescription;

    private String mdSympton;

    private Date mdAddDate;

    private Set<Diagnosis> diagnosis;

    /**
     * Empty constructor
     */
    public MentalDisease() {
        
    }

    /**
     * Costructor with parameters
     *
     * @param mdadmin
     * @param mdType
     * @param mdName
     * @param mdDescription
     * @param mdSympton
     * @param mdAddDate
     */
    public MentalDisease(Admin mdadmin, EnumMentalDisease mdType, String mdName, String mdDescription, String mdSympton, Date mdAddDate) {
        this.mdadmin = mdadmin;
        this.mdType = mdType;
        this.mdName = mdName;
        this.mdDescription = mdDescription;
        this.mdSympton = mdSympton;
        this.mdAddDate = mdAddDate;
    }

    //Getters & Setters
    public Long getIdMentalDisease() {
        return idMentalDisease;
    }

    public void setIdMentalDisease(Long idMentalDisease) {
        this.idMentalDisease = idMentalDisease;
    }

    public Admin getAdmin() {
        return mdadmin;
    }

    public void setAdmin(Admin mdadmin) {
        this.mdadmin = mdadmin;
    }

    public EnumMentalDisease getMdType() {
        return mdType;
    }

    public void setMdType(EnumMentalDisease mdType) {
        this.mdType = mdType;
    }

    public String getMdName() {
        return mdName;
    }

    public void setMdName(String mdName) {
        this.mdName = mdName;
    }

    public String getMdDescription() {
        return mdDescription;
    }

    public void setMdDescription(String mdDescription) {
        this.mdDescription = mdDescription;
    }

    public String getMdSympton() {
        return mdSympton;
    }

    public void setMdSympton(String mdSympton) {
        this.mdSympton = mdSympton;
    }

    public Date getMdAddDate() {
        return mdAddDate;
    }

    public void setMdAddDate(Date mdAddDate) {
        this.mdAddDate = mdAddDate;
    }

    public Set<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Set<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }

}
