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
public interface GameManager extends ESportsManager{
    /**
     * This method returns a Collection of {@link UserBean}, containing all users data.
     * @return Collection The collection with all {@link UserBean} data for users. 
     * @throws BusinessLogicException If there is any error while processing.
     */
    public Collection<Game> getAllGames() throws BusinessLogicException;
    public Collection<Game> updateGame(Object obj) throws BusinessLogicException;
    public void createGame(Object obj) throws BusinessLogicException;
    public void deleteGame(Long id) throws BusinessLogicException;
    public Collection<Game> findByName(String name) throws BusinessLogicException;
    public Collection<Game> findByGenre(String genre) throws BusinessLogicException;
    public Collection<Game> findByPlatform(String genre) throws BusinessLogicException;
    public Collection<Game> findByPVPType(String genre) throws BusinessLogicException;
    public Collection<Game> findByReleaseDate(String genre) throws BusinessLogicException;
    public Collection<Game> findGamesCreatedByAdmin(String genre) throws BusinessLogicException;
}
