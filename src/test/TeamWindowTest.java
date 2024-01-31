/*package test;
import businessLogic.TeamManager;
import businessLogic.TeamManagerImplementation;
import controller.TeamWindowController;
import java.util.concurrent.TimeoutException;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import main.Application;
import model.Player;
import model.Team;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.control.TableViewMatchers.containsRow;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;

public class TeamWindowTest {

    @Before
    public void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Application.class);
    }

    @After
    public void tearDown() {
        // Close the application after each test
        Platform.exit();
    }

    @Test
    public void testCreateTeam(FxRobot robot) {
        // Perform UI interactions using the robot
        robot.clickOn("#tfNombre").write("New Team");
        robot.clickOn("#tfCoach").write("New Coach");
        robot.clickOn("#dpFundacion").type(KeyCode.ENTER);
        robot.clickOn("#btnCrear");

        // You may add assertions to verify the result
        // For example, assert that the new team is added to the TableView
        TableView<Team> tableView = robot.lookup("#tbTeam").queryTableView();
        // Add your assertions here
    }

    @Test
    public void testModifyTeam(FxRobot robot) {
        // Perform UI interactions to select a team and modify its details
        robot.clickOn("#tbTeam").clickOn(MouseButton.PRIMARY);
        robot.clickOn("#tfNombre").write("Modified Team");
        robot.clickOn("#tfCoach").write("Modified Coach");
        robot.clickOn("#dpFundacion").type(KeyCode.ENTER);
        robot.clickOn("#btnModificar");

        // You may add assertions to verify the result
        // For example, assert that the team details are modified in the TableView
        TableView<Team> tableView = robot.lookup("#tbTeam").queryTableView();
        // Add your assertions here
    }

    @Test
    public void testDeleteTeam(FxRobot robot) {
        // Perform UI interactions to select a team and delete it
        robot.clickOn("#tbTeam").clickOn(MouseButton.PRIMARY);
        robot.clickOn("#btnEliminar");

        // You may add assertions to verify the result
        // For example, assert that the deleted team is not present in the TableView
        TableView<Team> tableView = robot.lookup("#tbTeam").queryTableView();
        // Add your assertions here
    }

    @Test
    public void testFindTeamByName(FxRobot robot) {
        // Perform UI interactions to find a team by its name
        robot.clickOn("#cmbBusqueda").clickOn("Por nombre");
        robot.clickOn("#tfNombre").write("TeamToFind");
        robot.clickOn("#btnBuscar");

        // Verify that the TableView contains the expected team
        TableView<Team> tableView = robot.lookup("#tbTeam").queryTableView();
        verifyThat(tableView, hasNumRows(1));
        verifyThat(tableView, containsRow(""));
    }
}*/
