/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EventManager;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import businessLogic.GameManager;
import businessLogic.TeamManager;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * This is the base class for UI controllers in this application. It contains
 * common methods and references for objects used by UI controllers
 *
 * @author Andoni Sanz
 */
public class GenericController {

    /**
     * Logger object used to log messages for application.
     */
    protected static final Logger LOGGER = Logger.getLogger("controller");
    /**
     * Maximum text fields length.
     */
    protected final int MAX_LENGTH = 255;
    /**
     * The business logic GameManager object containing all business methods.
     */
    protected GameManager gameManager;
    /**
     * The business logic TeamManager object containing all business methods.
     */
    protected TeamManager teamManager;
    /**
     * The business logic EventManager object containing all business methods.
     */
    protected EventManager eventManager;
    /**
     * Sets the business logic object to be used by this UI controller.
     *
     * @param usersManager An object implementing {@link UsersManager}
     * interface.
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }
    
    
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
    
    
    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the
     * controller to the Stage object in order to make its initialization. Note
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected Stage stage;

    /**
     * Gets the Stage object related to this controller.
     *
     * @return The Stage object initialized by this controller.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Sets the Stage object related to this controller.
     *
     * @param stage The Stage object to be initialized.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows an error message in an alert dialog.
     *
     * @param errorMsg The error message to be shown.
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR,
                errorMsg,
                ButtonType.OK);
        /*alert.getDialogPane().getStylesheets().add(
              getClass().getResource("/view/customCascadeStyleSheet.css").toExternalForm());*/
        alert.showAndWait();

    }

    /**
     * Action event handler for help button. It shows a Stage containing a scene
     * with a web viewer showing a help page for the window.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleHelpAction(ActionEvent event) {

    }

    /**
     * Action event handler for print button. It shows a JFrame containing a
     * report. This JFrame allows to print the report.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleImprimirAction(ActionEvent event) {

    }
}
