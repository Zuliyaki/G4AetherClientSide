package entities;

import java.io.Serializable;

import java.util.Date;


import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author unaibAndLeire
 */

@XmlRootElement
public class User implements Serializable {
    private String user_type;
    private String dni;
    private String fullName;
    private Date birthDate;
    private String password;
    private Integer phoneNumber;
    private String email;
    

    /**
     * Empty constructor
     */
    public User() {
    }

    public User(String dni, String fullName, Date birthDate, String password, Integer phoneNumber, String email, String userType) {
        this.dni = dni;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.user_type = userType;
    }

    //Getters & Setters
    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
    
    
}
