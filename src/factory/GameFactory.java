/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;

/**
 *
 * @author Andoni Sanz
 */
public class GameFactory {
        
    public static GameManager getGameManager() {
        //return the Implementation of Signable interface
        return new GameManagerImplementation();
    }
}

