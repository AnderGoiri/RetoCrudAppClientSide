/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * The {@code SignableImplementation} class is an implementation of the
 * {@code Signable} interface that provides business logic for user
 * authentication and registration using a RESTful service.
 *
 * <p>
 * It utilizes a web client to communicate with the RESTful service and perform
 * login and signup operations.</p>
 *
 * <p>
 * <strong>Author:</strong> Andoni Sanz Alcalde</p>
 *
 * @see Signable
 * @see User
 * @see CredentialsException
 * @see EmailAlreadyExistsException
 * @see ServerErrorException
 */
public class SignableImplementation implements Signable {

    private UserRestClient webClient;
    private static final Logger LOGGER = Logger.getLogger("GameManagerImplementation");

    /**
     * Creates a {@code SignableImplementation} object. It constructs a web
     * client for accessing a RESTful service that provides business logic in an
     * application server.
     */
    public SignableImplementation() {
        webClient = new UserRestClient();
    }

    @Override
    public User logIn(User u) throws IOException, CredentialsException, EmailAlreadyExistsException, ServerErrorException {
        User user = new User();
        try {
            LOGGER.info("Logging in user from REST service (XML).");
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
        User user = new User();
        try {
            LOGGER.info("Logging in user from REST service (XML).");
            // Call the logIn_XML method on webClient
            webClient.create_XML(u);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Exception during login{0}", ex.getMessage());
            // Handle exception appropriately, or throw your custom exceptions
            throw new ServerErrorException("Error during login\n" + ex.getMessage());
        }
        return u;
    }
}
