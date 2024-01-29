/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.ServerErrorException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Organizer;
import rest.OrganizerRestClient;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class OrganizerManagerImplementation implements OrganizerManager {

    private OrganizerRestClient webclient;
    private static final Logger LOGGER = Logger.getLogger(OrganizerManagerImplementation.class.getName());

    
    
    @Override
    public Organizer find(String id) {
        Organizer organizer = null;
        try {
            LOGGER.log(Level.INFO, "OrganizerManager: Finding organizer by ID: {0} (XML).", id);
            //Ask webClient for all users' data.
            organizer = webclient.find_XML(Organizer.class, id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "OrganizerManager: Exception finding desired organizer, {0}",
                    ex.getMessage());
            ex.printStackTrace();
        }
        return organizer;
    }

}
