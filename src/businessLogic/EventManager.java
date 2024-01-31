/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import java.util.Collection;
import model.Event;

/**
 * Business logic interface encapsulating business methods for events
 * management.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public interface EventManager {

    public Collection<Event> findAllEvents() throws BusinessLogicException;

    public Collection<Event> findEventsByOrganizer() throws BusinessLogicException;

    public Collection<Event> findEventsByGame(String gameName) throws BusinessLogicException;

    public Collection<Event> findEventsByONG(String ongName) throws BusinessLogicException;

    public Collection<Event> findEventsWonByPlayer() throws BusinessLogicException;

    public Collection<Event> findEventsWonByTeam() throws BusinessLogicException;

    public void deletePlayerEventByEventId() throws BusinessLogicException;

    public void deleteTeamEventByEventId() throws BusinessLogicException;

    public void createEvent(Event newEvent) throws BusinessLogicException;

    public void modifyEvent(Event selectedEvent) throws BusinessLogicException;

    public void deleteEvent(Event selectedEvent) throws BusinessLogicException;
}
