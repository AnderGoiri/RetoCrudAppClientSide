/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import model.Admin;
import model.Game;
import model.User;

/**
 *
 * @author Andoni Sanz
 */
public interface GameManager {
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Collection<Game> getAllGames() throws BusinessLogicException;
    public Collection<Game> updateGame(Object obj) throws BusinessLogicException;
    public Collection<Game> createGame(Object obj) throws BusinessLogicException;
    public Collection<Game> deleteGame(Long id) throws BusinessLogicException;
}
