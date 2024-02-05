/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import businessLogic.TeamManager;
import businessLogic.TeamManagerImplementation;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
public class TeamFactory {
    public static TeamManager getTeamManager() {
        return new TeamManagerImplementation();
    }
}
