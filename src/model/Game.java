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
@XmlRootElement(name = "game")
public class Game implements Serializable {

    private SimpleLongProperty id;

    private SimpleStringProperty name;
    private SimpleStringProperty genre;
    private SimpleStringProperty platform;

    private SimpleObjectProperty<PVPType> PVPType;

    private SimpleObjectProperty<Date> releaseDate;

    public Game() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.genre = new SimpleStringProperty();
        this.platform = new SimpleStringProperty();
        this.PVPType = new SimpleObjectProperty<PVPType>();
        this.releaseDate = new SimpleObjectProperty<Date>();
    }

    public Game(Long id, /*Admin admin, Set<Event> events,*/ String name, String genre, String platform, PVPType PVPType, Date releaseDate) {
        this.id = new SimpleLongProperty(id);
        /*this.admin = admin;
        this.events = events;*/
        this.name = new SimpleStringProperty(name);
        this.genre = new SimpleStringProperty(genre);
        this.platform = new SimpleStringProperty(platform);
        this.PVPType = new SimpleObjectProperty<PVPType>(PVPType);
        this.releaseDate = new SimpleObjectProperty<Date>(releaseDate);
    }

    @XmlElement(name = "id")
    public Long getId() {
        return this.id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @XmlElement(name = "name")
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    @XmlElement(name = "genre")
    public String getGenre() {
        return this.genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    @XmlElement(name = "platform")
    public String getPlatform() {
        return this.platform.get();
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }

    @XmlElement(name = "PVPType")
    public PVPType getPVPType() {
        return this.PVPType.get();
    }

    public void setPVPType(PVPType PVPType) {
        this.PVPType.set(PVPType);
    }

    @XmlElement(name = "releaseDate")
    public Date getReleaseDate() {
        return this.releaseDate.get();
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate.set(releaseDate);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) {
            return false;
        }
        Game other = (Game) obj;
        if ((this.name == null && other.name != null)
                || (this.name != null && !this.name.get().equals(other.name.get()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name.get();
    }
}
