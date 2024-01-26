/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andoni Sanz
 */
@XmlRootElement(name="user")
public class User implements Serializable{

    private SimpleLongProperty id;
    
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleStringProperty name;
    private SimpleStringProperty surnames;
    private SimpleStringProperty user_type;
    
    private SimpleObjectProperty<Date> birthDate;

    public User() {
        this.id = new SimpleLongProperty();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.surnames = new SimpleStringProperty();
        this.birthDate = new SimpleObjectProperty<Date>();
        this.user_type = new SimpleStringProperty();
    }

    public User(Long id, String username, String password, String email, String name, String surnames, Date birthDate, String user_type) {
        this.id = new SimpleLongProperty(id);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.email = new SimpleStringProperty(email);
        this.name = new SimpleStringProperty(name);
        this.surnames = new SimpleStringProperty(surnames);
        this.birthDate = new SimpleObjectProperty<Date>(birthDate);
        this.user_type = new SimpleStringProperty(user_type);
    }
  
    
    @XmlElement(name="id")
    public Long getId() {
        return this.id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }
    
    @XmlElement(name="username")
    public String getUsername() {
        return this.username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    @XmlElement(name="password")
    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    @XmlElement(name="email")
    public String getEmail() {
        return this.email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @XmlElement(name="name")
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @XmlElement(name="surnames")
    public String getSurnames() {
        return this.surnames.get();
    }

    public void setSurnames(String surnames) {
        this.surnames.set(surnames);
    }

    @XmlElement(name="birthDate")
    public Date getBirthDate() {
        return this.birthDate.get();
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate.set(birthDate);
    }

    @XmlElement(name="user_type")
    public String getUser_type() {
        return this.user_type.get();
    }

    public void setUser_type(String user_type) {
        this.user_type.set(user_type);
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
}
