/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exceptions.BusinessLogicException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
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
    public Collection<Team> findAllTeams() throws ReadException;
    public List<Team> findTeamsByName(String name) throws ReadException;
    public List<Team> findTeamsByDate(String date) throws ReadException;
    public List<Team> findTeamsByCoach(String coach) throws ReadException;
    //public List<Team> findTeamsWithWins() throws BusinessLogicException;
    public List<Team> findMyTeams(Player player) throws ReadException;
    public void joinTeam(Team selectedTeam, Player player) throws UpdateException;
    public void createTeam(Team newTeam) throws CreateException;
    public void modifyTeam(Team selectedTeam) throws UpdateException;
    public void deleteTeam(Team selectedTeam) throws DeleteException;

}
