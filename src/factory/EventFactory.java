package factory;

import businessLogic.EventManager;
import businessLogic.EventManagerImplementation;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventFactory {

    public static EventManager getEventManager() {
        return new EventManagerImplementation();
    }
}
