/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import businessLogic.EventManager;
import businessLogic.EventManagerImplementation;
import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import businessLogic.TeamManager;
import businessLogic.TeamManagerImplementation;
import controller.EventsViewController;
import controller.GameWindowController;
import controller.TeamWindowController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Admin;
import model.Player;
import model.User;

/**
 *
 * @author Andoni Sanz
 */
public class GameStart extends javafx.application.Application{    
    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where the
     * application scene can be set. The first stage represents the Log In Window of the
     * application.
     */
    @Override
    public void start(Stage primaryStage) {    
        try {

            GameManager bussinessLogicController= new GameManagerImplementation();
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameWindow.fxml"));
            Parent root = loader.load();
            GameWindowController controller = loader.getController();
            controller.setGameManager(bussinessLogicController);
            User u = new User();
            u.setUser_type("player");
            
            
            controller.setStage(primaryStage);               
            
            
            Scene scene = new Scene(root, 1366, 768);

            controller.setScene(scene);
            controller.initStage(root);
            
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
