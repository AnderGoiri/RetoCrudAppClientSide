package businessLogic;

import exceptions.CredentialsException;
import exceptions.EmailAlreadyExistsException;
import exceptions.ServerErrorException;
import factory.Signable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import rest.UserRestClient;

/**
 *
 * @author Andoni Sanz Alcalde
 */
public class SignableImplementation implements Signable {

    private UserRestClient webClient;
    private static final Logger LOGGER = Logger.getLogger("GameManagerImplementation");

    public SignableImplementation() {
        webClient = new UserRestClient();
    }

    @Override
    public User logIn(User u) throws IOException, CredentialsException, EmailAlreadyExistsException, ServerErrorException {
        User user = new User();
        try {
            LOGGER.info("Loging in user from REST service (XML).");
            // Call the logIn_XML method on webClient
            user = (User) webClient.login_XML(u, User.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception during login{0}", ex.getMessage());
            // Handle exception appropriately, or throw your custom exceptions
            ex.printStackTrace();
            throw new ServerErrorException("Error during login\n" + ex.getMessage());
        }
        return user;
    }

    @Override
    public User signUp(User u) throws IOException, EmailAlreadyExistsException, ServerErrorException {
        return new User();
    }
}
