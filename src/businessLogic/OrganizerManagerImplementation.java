/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ServerErrorException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Organizer;
import rest.OrganizerRestClient;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class OrganizerManagerImplementation implements OrganizerManager {

    private OrganizerRestClient webClient;
    private static final Logger LOGGER = Logger.getLogger(OrganizerManagerImplementation.class.getName());

    @Override
    public Organizer find(String id) {
        Organizer organizer = null;
        try {
            LOGGER.log(Level.INFO, "OrganizerManager: Finding organizer by ID: {0} (XML).", id);
            organizer = webClient.find_XML(Organizer.class, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "OrganizerManager: Exception finding desired organizer, {0}",
                    ex.getMessage());
            ex.printStackTrace();
        }
        return organizer;
    }

    @Override
    public List<Organizer> findAll() {
        List<Organizer> organizers = null;
        try {
            LOGGER.info("OrganizerManager: Finding all organizers REST service (XML).");
            //Ask webClient for all gamesdata.
            organizers = webClient.findAll_XML(new GenericType<List<Organizer>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "OrganizerManager: Exception finding all organizers{0}",
                    ex.getMessage());
        }
        return organizers;
    }

}
