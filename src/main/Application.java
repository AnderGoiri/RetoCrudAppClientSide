package main;

import java.io.IOException;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import controller.LogInController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Application class represents the main entry point of the JavaFX
 * application. It initializes the primary stage (main window) and sets up the
 * user interface of the application.
 *
 * <p>
 * The primary stage represents the Log In Window of the application.
 * </p>
 *
 * <p>
 * The main method is provided as the entry point of the application.
 * </p>
 *
 * <p>
 * This class extends {@link javafx.application.Application}.
 * </p>
 *
 * @author Jagoba Bartolomé Barroso
 * @author Ander Goirigolzarri Iturburu
 * @author Andoni Sanz Alcalde
 */
public class Application extends javafx.application.Application {

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LogInFXML.fxml"));
            Parent root = loader.load();
            LogInController controller = loader.getController();
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main method serves as the entry point of the application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
