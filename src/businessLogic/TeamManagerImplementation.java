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
import model.Team;
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
            throw new BusinessLogicException("Error finding all teams\n" + e.getMessage());
        }
        return teams;
    }

    @Override
    public List<Team> findTeamsByName() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Team> findTeamsByDate() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Team> findTeamsByCoach() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Team> findTeamsWithWins() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Team> findMyTeams() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void joinTeam() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createTeam() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifyTeam() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteTeam() throws BusinessLogicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}