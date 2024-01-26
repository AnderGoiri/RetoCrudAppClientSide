package logic;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import factory.Signable;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Player;
/*import libraries.ApplicationPDU;
import libraries.MessageType;*/
import model.User;
import rest.AdminRestClient;
import rest.PlayerRestClient;
import rest.UserRestClient;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class SignableImplementation implements Signable{
      
    private UserRestClient webClient;
    private PlayerRestClient webClientPlayer;
    private static final Logger LOGGER=Logger.getLogger("GameManagerImplementation");
    //TODO 
    /**
     * The IP address for the socket communication.
     */
    /*private ResourceBundle configFile = ResourceBundle.getBundle("properties.Config");
    private final String IP = configFile.getString("IP");*/
    /**
    * The port number for the socket communication.
    */
    //private final int PUERTO = Integer.valueOf(configFile.getString("PORT_C"));
    /**
     * The client socket.
     */
    public SignableImplementation(){
        webClient=new UserRestClient();
    }

    /**
     * This method writes a User through the Socket with the MessageType indicating that its a login. 
     * It returns a User with all the necessary data and a MessageType indicating any exception.
     * @param u
     * @throws IOException, ClassNotFoundException, CredentialsException, EmailAlreadyExistsException
     * @throws exceptions.EmailAlreadyExistsException
     */
    @Override
    public User logIn(User u) throws IOException, CredentialsException, EmailAlreadyExistsException, ServerErrorException {
         //List<User> users = null;
        User user = new User();
    try {
        LOGGER.info("GameManager: Logsing in user from REST service (XML).");
        
        // Call the logIn_XML method on webClient
        user = (User)webClient.login_XML(u, User.class);

        
    } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "GameManager: Exception during login{0}", ex.getMessage());
        // Handle exception appropriately, or throw your custom exceptions
        throw new ServerErrorException("Error during login\n" + ex.getMessage());
    }

        return user;
    }

    @Override
    public User signUp(User u) throws IOException, EmailAlreadyExistsException, ServerErrorException {
       

        return new User();
    }
}
