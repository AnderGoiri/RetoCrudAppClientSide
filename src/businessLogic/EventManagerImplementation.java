/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final Logger LOGGER = Logger.getLogger("EventManagerImplementation");

    public EventManagerImplementation() {
        webclient = new EventRestClient();
    }

    @Override
    public void createEvent(Event newEvent) throws BusinessLogicException {
        try {
            LOGGER.info("EventManager: Creating Event.");
            webclient.create_XML(newEvent);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception creating event{0}", e.getMessage());
            throw new BusinessLogicException("Error creating team\n" + e.getMessage());
        }
    }

    @Override
    public Collection<Event> findAllEvents() throws BusinessLogicException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding all events.");
            events = webclient.findAll_XML(new GenericType<List<Event>>() {
            });
        } catch (Exception ex) {
            LOGGER.severe("Error finding all events: " + ex.getMessage());
            throw new BusinessLogicException("Error finding all events by Organizer" + ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsByOrganizer() throws BusinessLogicException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding all events by Organizer");
            //events = webclient
            //      .findEventsByOrganizer_XML(new GenericType<List<Event>>() {
            //    });
        } catch (Exception ex) {
            throw new BusinessLogicException("Error finding all events by Organizer" + ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsByGame(String gameName) throws BusinessLogicException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding events by game.");
            events = webclient.findEventsByGame_XML(new GenericType<List<Event>>() {
            }, gameName);
        } catch (Exception ex) {
            LOGGER.severe("Error finding events by game: " + ex.getMessage());
            throw new BusinessLogicException("Error finding events by game: " + ex.getMessage());
        }
        return events;
    }

    @Override
    public Collection<Event> findEventsWonByPlayer() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> findEventsWonByTeam() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> findEventsByONG(String ongName) throws BusinessLogicException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding events by ONG.");
            events = webclient.findEventsByONG_XML(new GenericType<List<Event>>() {
            }, ongName);
        } catch (Exception ex) {
            LOGGER.severe("Error finding events by ONG: " + ex.getMessage());
            throw new BusinessLogicException("Error finding events by ONG: " + ex.getMessage());
        }
        return events;
    }

    @Override
    public void deletePlayerEventByEventId() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTeamEventByEventId() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyEvent(Event selectedEvent) throws BusinessLogicException {
        try {
            LOGGER.info("EventManager: Modifying Event.");
            webclient.edit_XML(selectedEvent, String.valueOf(selectedEvent.getId()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception modifying event{0}", e.getMessage());
            throw new BusinessLogicException("Error modifying event\n" + e.getMessage());
        }
    }

    @Override
    public void deleteEvent(Event selectedEvent) throws BusinessLogicException {
        try {
            LOGGER.info("EventManager: Deleting Event.");
            webclient.remove(String.valueOf(selectedEvent.getId()));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EventManager: exception deleting event{0}", e.getMessage());
            throw new BusinessLogicException("Error deleting event\n" + e.getMessage());
        }
    }

}
