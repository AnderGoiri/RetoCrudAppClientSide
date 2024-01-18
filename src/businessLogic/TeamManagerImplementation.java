/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.List;
import java.util.logging.Logger;
import model.Team;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class TeamManagerImplementation implements TeamManager {
    private PlayerRestClient webClient;
    private static final Logger LOGGER=Logger.getLogger("TeamManagerImplementation");
    
    @Override
    public List<Team> findAllTeams() throws BusinessLogicException {
        List<Team> teams = null;
        try {
            LOGGER.info("TeamManager: Finding all teams.");
            teams 
        } catch (Exception e) {
            
        }
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
