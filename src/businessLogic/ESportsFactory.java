package businessLogic;

/**
 * The {@code ESportsFactory} class is responsible for providing instances of the {@code ESportsManager} interface.
 * It serves as a factory for creating instances of the business logic manager related to eSports functionality.
 * 
 * <p>This factory follows the Factory Method design pattern, allowing easy substitution of different
 * implementations of the {@code ESportsManager} interface without changing the client code.</p>
 * 
 * <p>Usage example:</p>
 * <pre>{@code
 * ESportsManager manager = ESportsFactory.getESportsManager();
 * }</pre>
 * 
 * <p>The factory method {@code getESportsManager()} returns an instance of the {@code ESportsManager}
 * interface, typically an implementation class like {@code ESportsManagerImplementation}.</p>
 * 
 * <p><strong>Author:</strong> Andoni Sanz</p>
 * 
 * @see ESportsManager
 * @see ESportsManagerImplementation
 */
public class ESportsFactory {
    
    /**
     * Returns an instance of the {@code ESportsManager} interface.
     * 
     * <p>This method is used to obtain a concrete implementation of the {@code ESportsManager} interface,
     * allowing clients to interact with the business logic related to eSports functionality.</p>
     * 
     * @return an instance of the {@code ESportsManager} interface.
     * 
     * @see ESportsManager
     * @see ESportsManagerImplementation
     */
    public static ESportsManager getESportsManager() {
        //return the Implementation of Signable interface
        return new ESportsManagerImplementation();
    }  
}
