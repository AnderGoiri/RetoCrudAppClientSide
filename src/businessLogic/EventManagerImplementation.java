/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.logging.Logger;
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
    
    
    
    
}
