/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericType;
import model.Event;
import rest.EventRestClient;

/**
 * This class implements {@link EventManager} business logic interface using a
 * RESTful web client to access bussines logic in an Java EE application server.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventManagerImplementation implements EventManager {

    private EventRestClient webclient;
    private static final Logger LOGGER = Logger.getLogger(EventManagerImplementation.class.getName());

    public EventManagerImplementation() {
        webclient = new EventRestClient();
    }

    @Override
    public void createEvent(Event newEvent) throws CreateException {
        try {
            LOGGER.info("EventManager: Creating Event.");
            webclient.create_XML(newEvent);
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception creating event: {0}", e.getMessage());
            throw new CreateException(e.getMessage());
        }
    }

    @Override
    public Collection<Event> findAllEvents() throws ReadException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding all events.");
            events = webclient.findAll_XML(new GenericType<List<Event>>() {
            });
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "Error finding all events: {0}", ex.getMessage());
            throw new ReadException(ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsByOrganizer() throws ReadException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding all events by Organizer");
            //events = webclient
            //      .findEventsByOrganizer_XML(new GenericType<List<Event>>() {
            //    });
        } catch (WebApplicationException ex) {
            throw new ReadException(ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsByGame(String gameName) throws ReadException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding events by game.");
            events = webclient.findEventsByGame_XML(new GenericType<List<Event>>() {
            }, gameName);
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "Error finding events by game: {0}", ex.getMessage());
            throw new ReadException(ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsWonByPlayer() throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> findEventsWonByTeam() throws ReadException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> findEventsByONG(String ongName) throws ReadException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding events by ONG.");
            events = webclient.findEventsByONG_XML(new GenericType<List<Event>>() {
            }, ongName);
        } catch (WebApplicationException ex) {
            LOGGER.log(Level.SEVERE, "Error finding events by ONG: {0}", ex.getMessage());
            throw new ReadException(ex.getMessage());
        }
        return events;
    }

    @Override
    public void deletePlayerEventByEventId() throws DeleteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTeamEventByEventId() throws DeleteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyEvent(Event selectedEvent) throws UpdateException {
        try {
            LOGGER.info("EventManager: Modifying Event.");
            webclient.edit_XML(selectedEvent, String.valueOf(selectedEvent.getId()));
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception modifying event{0}", e.getMessage());
            throw new UpdateException(e.getMessage());
        }
    }

    @Override
    public void deleteEvent(Event selectedEvent) throws DeleteException {
        try {
            LOGGER.info("EventManager: Deleting Event.");
            webclient.remove(String.valueOf(selectedEvent.getId()));
        } catch (WebApplicationException e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception deleting event{0}", e.getMessage());
            throw new DeleteException(e.getMessage());
        }
    }

}