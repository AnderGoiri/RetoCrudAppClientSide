/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */

public class TeamEvent {

    private TeamEventId Id;

    private Team team;

    private Event event;

    private Result result;

    public TeamEventId getId() {
        return Id;
    }

    public Team getTeam() {
        return team;
    }

    public Event getEvent() {
        return event;
    }

    public Result getResult() {
        return result;
    }

    public void setId(TeamEventId Id) {
        this.Id = Id;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
