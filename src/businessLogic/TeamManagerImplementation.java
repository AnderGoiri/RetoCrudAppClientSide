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
import static javafx.scene.input.KeyCode.T;
import javax.ws.rs.core.GenericType;
import model.Player;
import model.PlayerTeam;
import model.Team;
import model.User;
import rest.PlayerRestClient;
import rest.TeamRestClient;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class TeamManagerImplementation implements TeamManager {
    private TeamRestClient webClient;
    private static final Logger LOGGER=Logger.getLogger("TeamManagerImplementation");

    public TeamManagerImplementation() {
        this.webClient = new TeamRestClient();
    }
    
    @Override
    public Collection<Team> findAllTeams() throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding all teams."); 
            teams = webClient.findAllTeams_XML(new GenericType<List<Team>>() {});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception finding all teams{0}", e.getMessage());
            throw new BusinessLogicException("Finding all teams\n" + e.getMessage());
        }
        return teams;
    }

    @Override
    public List<Team> findTeamsByName(String name) throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding teams by name.");
            teams = webClient.findTeamsByName_XML(new GenericType<List<Team>>() {}, name);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception finding teams by name{0}", e.getMessage());
            throw new BusinessLogicException("Error finding teams by name\n" + e.getMessage());
        }
        return teams;
    }

    @Override
    public List<Team> findTeamsByDate(String date) throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding teams by date.");
            teams = webClient.findTeamsByDate_XML(new GenericType<List<Team>>() {}, date);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception finding teams by date{0}", e.getMessage());
            throw new BusinessLogicException("Error finding teams by date\n" + e.getMessage());
        }
        return teams;
    }
    
    @Override
    public List<Team> findTeamsByCoach(String coach) throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding teams by coach.");
            teams = webClient.findTeamsByCoach_XML(new GenericType<List<Team>>() {}, coach);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception finding teams by coach{0}", e.getMessage());
            throw new BusinessLogicException("Error finding teams by coach\n" + e.getMessage());
        }
        return teams;
    }
    
    @Override
    public List<Team> findTeamsWithWins() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Team> findMyTeams(Player player) throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding the player's teams.");
            teams = webClient.findMyTeams_XML(new GenericType<List<Team>>() {}, player.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception finding the player's teams{0}", e.getMessage());
            throw new BusinessLogicException("Error finding the player's teams\n" + e.getMessage());
        }
        return teams;
    }
    
    @Override
    public void joinTeam(Team selectedTeam, Player player) throws BusinessLogicException {
        try {
            LOGGER.info("TeamManager: Modifying team.");
            webClient.joinTeam_XML(selectedTeam.getId(), player.getId());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception joining the team{0}", e.getMessage());
            throw new BusinessLogicException("Error joining team\n" + e.getMessage());
        }
    }

    @Override
    public void modifyTeam(Team selectedTeam) throws BusinessLogicException {
        try {
            LOGGER.info("TeamManager: Modifying team.");
            webClient.updateTeam_XML(selectedTeam, Team.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception modifying team{0}", e.getMessage());
            throw new BusinessLogicException("Error modifying team\n" + e.getMessage());
        }
    }

    @Override
    public void deleteTeam(Team selectedTeam) throws BusinessLogicException {
        try {
            LOGGER.info("TeamManager: Deleting team.");
            webClient.deleteTeam_XML(selectedTeam);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeanManager: Exception modifying team{0}", e.getMessage());
            throw new BusinessLogicException("Error modifying team\n" + e.getMessage());
        }
    }

    @Override
    public void createTeam(Team newTeam) throws BusinessLogicException {
        try {
            LOGGER.info("TeamManager: Creating team.");
            webClient.createTeam_XML(newTeam, Team.class);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "TeamManager: Exception creating team{0}", e.getMessage());
            throw new BusinessLogicException("Error creating team\n" + e.getMessage());
        }
    }
   
}
