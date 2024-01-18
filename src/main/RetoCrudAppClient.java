/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import controller.GameWindowController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Andoni Sanz
 */
public class RetoCrudAppClient extends javafx.application.Application{    
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
            //Create Bussines Logic Controller to be passed to UI controllers
            GameManager bussinessLogicController= new GameManagerImplementation();
        
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameWindow.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameWindow.fxml"));
            Parent root = loader.load();
            GameWindowController controller = loader.getController();
            controller.setUsersManager(bussinessLogicController);
            
            controller.setStage(primaryStage);               
                
            controller.initStage(root);
            //primaryStage.show();
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
