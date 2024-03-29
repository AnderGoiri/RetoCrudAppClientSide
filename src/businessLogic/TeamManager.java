/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import java.util.Collection;
import java.util.List;
import model.Player;
import model.Team;
import model.User;

/**
 * Business logic interface encapsulating business methods for teams
 * management.
 *
 * @author Jagoba Bartolomé Barroso
 */
public interface TeamManager {
    public Collection<Team> findAllTeams() throws BusinessLogicException;
    public List<Team> findTeamsByName(String name) throws BusinessLogicException;
    public List<Team> findTeamsByDate(String date) throws BusinessLogicException;
    public List<Team> findTeamsByCoach(String coach) throws BusinessLogicException;
    //public List<Team> findTeamsWithWins() throws BusinessLogicException;
    public List<Team> findMyTeams(Player player) throws BusinessLogicException;
    public void joinTeam(Team selectedTeam, Player player) throws BusinessLogicException;
    public void createTeam(Team newTeam) throws BusinessLogicException;
    public void modifyTeam(Team selectedTeam) throws BusinessLogicException;
    public void deleteTeam(Team selectedTeam) throws BusinessLogicException;

}
