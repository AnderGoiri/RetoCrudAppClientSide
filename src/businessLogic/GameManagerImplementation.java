/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import model.Admin;
import model.Game;
import model.User;
import rest.AdminRestClient;
import rest.GameRestClient;
import rest.UserRestClient;

/**
 *
 * @author Andoni Sanz
 */
public class GameManagerImplementation implements GameManager {

    //REST users web client
    private AdminRestClient webClient;
    private static final Logger LOGGER = Logger.getLogger("GameManagerImplementation");

    /**
     * Create a GameManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public GameManagerImplementation() {
        webClient = new AdminRestClient();
    }

    @Override
    public Collection<Game> getAllGames() throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findAllGames_XML(new GenericType<List<Game>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findByName(String name) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding games by Name REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findGamesByName_XML(new GenericType<List<Game>>() {
            }, name);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding games by name",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding games by name\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findByGenre(String genre) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findGamesByGenre_XML(new GenericType<List<Game>>() {
            }, genre);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findByPlatform(String platform) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findGamesByPlatform_XML(new GenericType<List<Game>>() {
            }, platform);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findByPVPType(String pvptype) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findGamesByPVPType_XML(new GenericType<List<Game>>() {
            }, pvptype);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findByReleaseDate(String releaseDate) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findGamesByReleaseDate_XML(new GenericType<List<Game>>() {
            }, releaseDate);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public Collection<Game> findGamesCreatedByAdmin(String genre) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Game> updateGame(Object obj) throws BusinessLogicException {
        List<Game> games = null;
        try {
            LOGGER.info("GameManager: Update Game gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            webClient.updateGame_XML(obj, Game.class);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception updating game}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating game\n" + ex.getMessage());
        }
        return games;
    }

    @Override
    public void createGame(Object obj) throws BusinessLogicException {
        try {
            LOGGER.info("GameManager: Create Game gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            webClient.createGame_XML(obj);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception Creating game}",
                    ex.getMessage());
            throw new BusinessLogicException("Error Creating game\n" + ex.getMessage());
        }
    }

    @Override
    public void deleteGame(Long id) throws BusinessLogicException {
        try {
            LOGGER.info("GameManager: Delete Game gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            webClient.deleteGame_XML(id.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception Deleting game}",
                    ex.getMessage());
            throw new BusinessLogicException("Error Deleting game\n" + ex.getMessage());
        }
    }

    @Override
    public void passwordRecovery(String mail) throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
