/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

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

    public Collection<Event> findEventsByGame() throws BusinessLogicException;

    public Collection<Event> findEventsWonByPlayer() throws BusinessLogicException;

    public Collection<Event> findEventsWonByTeam() throws BusinessLogicException;

    public Collection<Event> findEventsByONG() throws BusinessLogicException;

    public void deletePlayerEventByEventId() throws BusinessLogicException;

    public void deleteTeamEventByEventId() throws BusinessLogicException;

    public void createEvent(Event newEvent) throws BusinessLogicException;

    public void modifyEvent(Event selectedEvent) throws BusinessLogicException;

    public void deleteEvent(Event selectedEvent) throws BusinessLogicException;
}
