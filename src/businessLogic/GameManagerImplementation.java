/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

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
public class GameManagerImplementation implements GameManager{
    //REST users web client
    private GameRestClient webClient;
    private static final Logger LOGGER=Logger.getLogger("GameManagerImplementation");

    /**
     * Create a GameManagerImplementation object. It constructs a web client for 
     * accessing a RESTful service that provides business logic in an application
     * server.
     */
    public GameManagerImplementation(){
        webClient=new GameRestClient();
    }
   
    
    @Override
    public Collection<Game> getAllGames() throws BusinessLogicException {
        List<Game> games =null;
        try{
            LOGGER.info("GameManager: Finding all gamesfrom REST service (XML).");
            //Ask webClient for all gamesdata.
            games = webClient.findAll_XML(new GenericType<List<Game>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all games{0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all games\n"+ex.getMessage());
        }
        return games;
    }
    
}