/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.List;
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
    public Collection<Event> findAllEvents() throws BusinessLogicException {
        List<Event> events = null;
        try {
            LOGGER.info("EventManager: finding all events by Organizer");
            events = webclient.findAll_XML(new GenericType<List<Event>>() {
            });
        } catch (Exception ex) {
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
    public Collection<Event> findEventsByGame() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public Collection<Event> findEventsByONG() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deletePlayerEventByEventId() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTeamEventByEventId() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
