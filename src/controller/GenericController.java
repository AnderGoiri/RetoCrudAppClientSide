/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EventManager;
import businessLogic.EventManagerImplementation;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import businessLogic.TeamManager;
import businessLogic.TeamManagerImplementation;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import javafx.stage.WindowEvent;
import model.Game;
import model.Player;
import model.User;

/**
 * This is the base class for UI controllers in this application. It contains
 * common methods and references for objects used by UI controllers
 *
 * @author Jagoba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz Alcalde
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

    protected static User user;

    protected static TableView<Game> tbGames;
    
    public static TableView<Game> getTbGames() {
        return tbGames;
    }

    public static void setTbGames(TableView<Game> tbGames) {
        GenericController.tbGames = tbGames;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Sets the business logic object to be used by this UI controller.
     *
     * @param gameManager An object implementing {@link GameManager} interface.
     */
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * Sets the business logic object to be used by this UI controller.
     *
     * @param teamManager An object implementing {@link TeamManager} interface.
     */
    public void setTeamManager(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    /**
     * Sets the business logic object to be used by this UI controller.
     *
     * @param eventManager An object implementing {@link EventManager}
     * interface.
     */
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * The Stage object associated to the Scene controlled by this controller.
     * This is an utility method reference that provides quick access inside the
     * controller to the Stage object in order to make its initialization. Note
     * that this makes Application, Controller and Stage being tightly coupled.
     */
    protected static Stage stage;
    protected static Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
    
    
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

    /**
     * Action event handler for print button. It shows a JFrame containing a
     * report. This JFrame allows to print the report.
     *
     * @param event The ActionEvent object for the event.
     */
    @FXML
    private void handleExitAction(ActionEvent event) {

    }

    @FXML
    private void handleEventsAction(ActionEvent event) {
        try {
            //Create Bussines Logic Controller to be passed to UI controllers
            EventManager eventLogicController= new EventManagerImplementation();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EventsView.fxml"));
            Parent root = loader.load();
            EventsViewController controller = loader.getController();
            
            controller.setEventManager(eventLogicController);
            controller.setStage(getStage());
            controller.setScene(getScene());
            setUser(user);
            //User user = new Player();
            //user.setId(Long.valueOf(3));
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleTeamAction(ActionEvent event) {
        try {
            TeamManager teamLogicController = new TeamManagerImplementation();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TeamView.fxml"));
            Parent root = loader.load();
            TeamWindowController controller = loader.getController();

            controller.setTeamManager(teamLogicController);
            controller.setStage(getStage());
            controller.setScene(getScene());
            //User user = new Player();
            //user.setId(Long.valueOf(3));
            setUser(user);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     @FXML
    private void handleGameAction(ActionEvent event) {
        try {
            //Create Bussines Logic Controller to be passed to UI controllers
            GameManager bussinessLogicController = new GameManagerImplementation();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameWindow.fxml"));
            Parent root = loader.load();
            GameWindowController controller = loader.getController();
            controller.setGameManager(bussinessLogicController);

            /*controller.setStage(getStage());
            controller.setScene(getScene());*/
            controller.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(GenericController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Event handler for the window close request.
     *
     * This method is triggered when a user attempts to close the window. It
     * logs the close request, creates a confirmation dialog asking the user to
     * confirm the closure, and handles the closure based on the user's
     * response. If the user confirms, the window is closed; otherwise, the
     * closure is canceled, and the window remains open. The method consumes the
     * event to prevent the default window close behavior.
     *
     * @param event The WindowEvent representing the window close request.
     */
    public void handleCloseRequest(WindowEvent event) {
        try {
            LOGGER.info("Window close requested...");
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Closure");
            alert.setHeaderText("Are you sure you want to close the app?");
            // Add confirmation and cancel buttons to the dialog
            ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);
            // Show the dialog and wait for user response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {
                // If the user confirms, close the window
                event.consume(); // Stops the default window close
                LOGGER.info("Window closed by user confirmation.");
                stage.close();
            } else {
                // If the user cancels, do not close the window
                event.consume(); // Stops the default window close
                LOGGER.info("Window closure canceled by user.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling window close request", e);
        }
    }

    /**
     * Handles the "Salir" button click event. Closes the current window.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    public void handleBtnClose(ActionEvent event) {
        try {
            LOGGER.info("Salir button clicked.");
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cerrar sesión");
            alert.setHeaderText("¿Desea cerrar su sesión actual?");

            // Add confirmation and cancel buttons to the dialog
            ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);


            // Show the dialog and wait for user response
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == confirmButton) {

                // Load the LoginFXML.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
                Parent root = loader.load();

                // Get the controller of the login window
                LogInController controller = loader.getController();

                // Set the primary stage (main window) to display the login window
                controller.setStage(stage);
                controller.initStage(root);
            } else {
                LOGGER.info("Window closure canceled by user.");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error handling Salir button click", ex);
        }
    }
}