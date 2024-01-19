/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleSetProperty;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */

public class Player extends User {

    private SimpleSetProperty<Team> teams;

    private SimpleSetProperty<PlayerEvent> playerevent;
    
    private SimpleIntegerProperty level;

    public void setTeams(SimpleSetProperty<Team> teams) {
        this.teams.set(teams);
    }

    public void setPlayerevent(SimpleSetProperty<PlayerEvent> playerevent) {
        this.playerevent.set(playerevent);
    }

    public void setLevel(Integer level) {
        this.level.set(level);
    }


    public Set<PlayerEvent> getPlayerevent() {
        return this.playerevent.get();
    }

    public Set<Team> getTeams() {
        return this.teams.get();
    }

    public Integer getLevel() {
        return this.level.get();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (super.getId() != null ? super.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same reference as this (the same object)
        if (this == obj) {
            return true;
        }
        // Check if the passed object is null or not an instance of Player
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Call the equals method of the base class (Player) to check equality in the class hierarchy
        if (!super.equals(obj)) {
            return false;
        }
        // Convert the passed object to Player to compare the IDs
        Player player = (Player) obj;
        // Compare the IDs using Objects.equals to handle null values
        return Objects.equals(super.getId(), player.getId());
    }

}
