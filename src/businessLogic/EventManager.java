/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ReadException;
import exceptions.BusinessLogicException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.UpdateException;
import java.util.Collection;
import model.Event;

/**
 * Business logic interface encapsulating business methods for events
 * management.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public interface EventManager {

    public Collection<Event> findAllEvents() throws ReadException;

    public Collection<Event> findEventsByOrganizer() throws ReadException;

    public Collection<Event> findEventsByGame(String gameName) throws ReadException;

    public Collection<Event> findEventsByONG(String ongName) throws ReadException;

    public Collection<Event> findEventsWonByPlayer() throws ReadException;

    public Collection<Event> findEventsWonByTeam() throws ReadException;

    public void deletePlayerEventByEventId() throws DeleteException;

    public void deleteTeamEventByEventId() throws DeleteException;

    public void createEvent(Event newEvent) throws CreateException;

    public void modifyEvent(Event selectedEvent) throws UpdateException;

    public void deleteEvent(Event selectedEvent) throws DeleteException;
}
