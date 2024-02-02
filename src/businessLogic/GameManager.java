/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import java.util.Collection;
import model.Game;

/**
 * The {@code GameManager} interface defines the contract for managing game-related functionality
 * in the eSports application. It extends the {@code ESportsManager} interface and specifies
 * methods for CRUD operations on games, as well as additional search functionality.
 * 
 * <p>Implementations of this interface provide the concrete logic for the specified functionality.</p>
 * 
 * <p><strong>Author:</strong> Andoni Sanz Alcalde</p>
 * 
 * @see ESportsManager
 * @see Game
 * @see BusinessLogicException
 */
public interface GameManager extends ESportsManager {
    
    /**
     * Retrieves a Collection of all games in the system.
     * 
     * @return a Collection of {@code Game} objects representing all games.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> getAllGames() throws BusinessLogicException;
    
    /**
     * Updates the details of a game in the system.
     * 
     * @param obj the object representing the game to be updated.
     * @return a Collection of updated {@code Game} objects.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> updateGame(Object obj) throws BusinessLogicException;
    
    /**
     * Creates a new game in the system.
     * 
     * @param obj the object representing the game to be created.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public void createGame(Object obj) throws BusinessLogicException;
    
    /**
     * Deletes a game from the system based on the specified ID.
     * 
     * @param id the ID of the game to be deleted.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public void deleteGame(Long id) throws BusinessLogicException;
    
    /**
     * Finds games by their name in the system.
     * 
     * @param name the name of the game to search for.
     * @return a Collection of {@code Game} objects matching the specified name.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findByName(String name) throws BusinessLogicException;
    
    /**
     * Finds games by their genre in the system.
     * 
     * @param genre the genre of the game to search for.
     * @return a Collection of {@code Game} objects matching the specified genre.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findByGenre(String genre) throws BusinessLogicException;
    
    /**
     * Finds games by their platform in the system.
     * 
     * @param platform the platform of the game to search for.
     * @return a Collection of {@code Game} objects matching the specified platform.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findByPlatform(String platform) throws BusinessLogicException;
    
    /**
     * Finds games by their PVP (Player vs. Player) type in the system.
     * 
     * @param pvpType the PVP type of the game to search for.
     * @return a Collection of {@code Game} objects matching the specified PVP type.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findByPVPType(String pvpType) throws BusinessLogicException;
    
    /**
     * Finds games by their release date in the system.
     * 
     * @param releaseDate the release date of the game to search for.
     * @return a Collection of {@code Game} objects matching the specified release date.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findByReleaseDate(String releaseDate) throws BusinessLogicException;
    
    /**
     * Finds games created by an admin in the system.
     * 
     * @param adminName the name of the admin who created the games.
     * @return a Collection of {@code Game} objects created by the specified admin.
     * 
     * @throws BusinessLogicException if there is any error while processing.
     * 
     * @see Game
     * @see BusinessLogicException
     */
    public Collection<Game> findGamesCreatedByAdmin(String adminName) throws BusinessLogicException;
}
