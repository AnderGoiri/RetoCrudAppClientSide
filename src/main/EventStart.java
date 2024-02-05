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
import controller.EventsViewController;
import factory.EventFactory;
import factory.GameFactory;
import factory.TeamFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Player;
import model.User;

/**
 *
 * @author Andoni Sanz
 */
public class EventStart extends javafx.application.Application {

    /**
     * This method is called when the JavaFX application is launched. It is used
     * to initialize the primary stage (the main window) and set up the user
     * interface of the application.
     *
     * @param primaryStage The primary stage for this application, where the
     * application scene can be set. The first stage represents the Log In
     * Window of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            //Create Bussines Logic Controller to be passed to UI controllers
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventsView.fxml"));
            Parent root = loader.load();
            EventsViewController controller = loader.getController();
            controller.setEventManager(EventFactory.getEventManager());
            controller.setGameManager(GameFactory.getGameManager());
            controller.setTeamManager(TeamFactory.getTeamManager());
            controller.setStage(primaryStage);

            User user = new Player();
            user.setUser_type("organizer");
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
