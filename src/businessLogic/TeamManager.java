/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.List;
import model.Team;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public interface TeamManager {
    public Collection<Team> findAllTeams() throws BusinessLogicException;
    public List<Team> findTeamsByName() throws BusinessLogicException;
    public List<Team> findTeamsByDate() throws BusinessLogicException;
    public List<Team> findTeamsByCoach() throws BusinessLogicException;
    public List<Team> findTeamsWithWins() throws BusinessLogicException;
    public List<Team> findMyTeams() throws BusinessLogicException;
    public void joinTeam() throws BusinessLogicException;
    public void createTeam() throws BusinessLogicException;
    public void modifyTeam() throws BusinessLogicException;
    public void deleteTeam() throws BusinessLogicException;
}