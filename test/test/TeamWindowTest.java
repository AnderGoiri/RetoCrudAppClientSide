package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import model.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import main.RetoCrudAppClient;
import model.Team;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamWindowTest extends ApplicationTest{

    @FXML
    private Label titulo;

    @FXML
    private HBox hBoxMenu;

    @FXML
    private TableView<Team> tbTeam;

    @FXML
    private TableColumn<Team, String> tbcolNombre;

    @FXML
    private TableColumn<Team, Date> tbcolFundacion;

    @FXML
    private TableColumn<Team, String> tbcolEntrenador;

    @FXML
    private Label lblNombre;

    @FXML
    private TextField tfNombre;

    @FXML
    private Label lblFundacion;

    @FXML
    private Label lblCoach;

    @FXML
    private TextField tfCoach;

    @FXML
    private DatePicker dpFundacion;

    @FXML
    private Button btnBuscar;

    @FXML
    private Label lblBusqueda;

    @FXML
    private ComboBox<String> cmbBusqueda;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnUnirse;

    @FXML
    private Label lblError;

    @FXML
    private Button btnEliminar;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(RetoCrudAppClient.class);
    }

    @Test
    public void test1InitStage(){
        verifyThat("#hBoxMenu", isVisible());
        verifyThat("#tfNombre", hasText(""));
        verifyThat("#tfFundacion", hasText(""));
        verifyThat("#tfCoach", hasText(""));
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnSalir", isEnabled());
        verifyThat("#tbTeam", isVisible());
    }
    
    @Test
    public void test2CreateTeam() {  
        tbTeam = lookup("#tableViewEvents").queryTableView();
        int rowCount = tbTeam.getItems().size();
        clickOn("#tfNombre");
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
}
