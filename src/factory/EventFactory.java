package factory;

import businessLogic.EventManager;
import businessLogic.EventManagerImplementation;

/**
 * A factory class for creating instances of {@link EventManager}. This factory
 * provides a method to obtain an instance of
 * {@link EventManagerImplementation}.
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventFactory {

    /**
     * Returns an instance of {@link EventManager} implemented by
     * {@link EventManagerImplementation}.
     *
     * @return An instance of {@link EventManager}.
     */
    public static EventManager getEventManager() {
        return new EventManagerImplementation();
    }
}
