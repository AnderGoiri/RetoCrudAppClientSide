/*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
 */
package model;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@XmlRootElement(name="team")
public class Team implements Serializable {

    private Long id;

    Set<Player> playersInTeam;
    private String name;

    private Date foundation;
    private String coach;
    private Set<TeamEvent> teamevents;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getFoundation() throws FileNotFoundException, IOException, ParseException{
        return foundation;
    }

    public String getCoach() {
        return coach;
    }

    @XmlTransient
    public Set<Player> getPlayersInTeam() {
        return playersInTeam;
    }

    @XmlTransient
    public Set<TeamEvent> getTeamevents() {
        return teamevents;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundation(Date foundation) {
        this.foundation = foundation;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same reference as this (the same object)
        if (this == obj) {
            return true;
        }
        // Check if the passed object is null or not an instance of Team
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Convert the passed object to Team to compare the IDs
        Team team = (Team) obj;
        // Compare the IDs using Objects.equals to handle null values
        return Objects.equals(this.getId(), this.getId());
    }

}
