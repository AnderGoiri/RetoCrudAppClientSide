/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.TeamStart;
import model.Team;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Jagoba Bartolomé Barroso
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TeamWindowControllerTest extends ApplicationTest {

    @FXML
    private TableView<Team> tbTeam;
    private TextField nombre;
    private TextField coach;

    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TeamStart.class);
    }

    @Test
    public void test1InitStage() {
        sleep(4000);
        verifyThat("#hBoxMenu", isVisible());
        verifyThat("#tfNombre", hasText(""));
        verifyThat("#tfCoach", hasText(""));
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnSalir", isEnabled());
        verifyThat("#tbTeam", isVisible());
    }

    @Test
    public void test2CreateTeam() {
        tbTeam = lookup("#tbTeam").queryTableView();
        int rowCount = tbTeam.getItems().size();

        clickOn("#tfNombre");
        String team = "team" + String.valueOf(new Random().nextInt(99) + 1);
        write(team);

        clickOn("#tfCoach");
        String coach = "coach" + String.valueOf(new Random().nextInt(99) + 1);
        write(coach);

        clickOn("#dpFundacion");
        write(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        press(KeyCode.ENTER);

        clickOn("#btnCrear");
        assertEquals("The row has not been added.", rowCount + 1, tbTeam.getItems().size());
        List<Team> teams = tbTeam.getItems();
        assertEquals("The team has not been added.", teams.stream().filter(t -> t.getName().equals(team) && t.getCoach().equals(coach)).count(), 1);

        sleep(3000);
    }

    @Test
    public void test3FindTeamByName() {
        clickOn("#btnLimpiar");

        tbTeam = lookup("#tbTeam").queryTableView();
        int rowCount = tbTeam.getItems().size();

        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        clickOn("#cmbBusqueda");
        clickOn("Por nombre");

        Team selectedTeam = (Team) tbTeam.getSelectionModel().getSelectedItem();
        String selectedName = selectedTeam.getName();
        write(selectedName);
        clickOn("#btnBuscar");
        rowCount = tbTeam.getItems().size();
        List<Team> teams = tbTeam.getItems();
        assertEquals("The search was successful", rowCount, teams.stream().filter(team -> team.getName().equals(selectedTeam.getName())).count());

        sleep(3000);
    }

    @Test
    public void test4FindTeamByCoach() {
        clickOn("#btnLimpiar");
        clickOn("#cmbBusqueda");
        clickOn("Todos");
        clickOn("#btnBuscar");

        tbTeam = lookup("#tbTeam").queryTableView();
        int rowCount = tbTeam.getItems().size();

        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);

        clickOn("#cmbBusqueda");
        clickOn("Por coach");

        Team selectedTeam = (Team) tbTeam.getSelectionModel().getSelectedItem();
        String selectedCoach = selectedTeam.getCoach();
        write(selectedCoach);
        clickOn("#btnBuscar");
        rowCount = tbTeam.getItems().size();
        List<Team> teams = tbTeam.getItems();
        assertEquals("The search was successful", rowCount, teams.stream().filter(team -> team.getCoach().equals(selectedTeam.getCoach())).count());

        sleep(3000);
    }

    @Test
    public void test5ModifyTeam() {
        tbTeam = lookup("#tbTeam").queryTableView();
        int rowCount = tbTeam.getItems().size();

        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        clickOn(row);

        nombre = lookup("#tfNombre").query();
        nombre.clear();
        String newTeamName = "New Team name" + String.valueOf(new Random().nextInt(99) + 1);;
        clickOn("#tfNombre");
        write(newTeamName);

        coach = lookup("#tfCoach").query();
        coach.clear();
        String newTeamCoach = "New Coach" + String.valueOf(new Random().nextInt(99) + 1);;
        clickOn("#tfCoach");
        write(newTeamCoach);

        clickOn("#dpFundacion");
        eraseText(30);
        Date newTeamFoundation = Date.from(Instant.now());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(newTeamFoundation);
        write(formattedDate.toString());

        clickOn("#btnModificar");

        rowCount = tbTeam.getItems().size();
        List<Team> teams = tbTeam.getItems();
        Team modifiedTeam = teams.get(rowCount - 1);

        assertEquals("Name modified successfully", modifiedTeam.getName(), newTeamName);

        assertEquals("Coach modified successfully", modifiedTeam.getCoach(), newTeamCoach);

        try {
            SimpleDateFormat dateFormatTable = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDateTable = dateFormatTable.format(modifiedTeam.getFoundation());
            assertEquals("Coach modified successfully", formattedDateTable, formattedDate);
        } catch (IOException ex) {
            Logger.getLogger(TeamWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(TeamWindowControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        sleep(3000);
    }

    @Test
    public void test6DeleteTeam() {
        tbTeam = lookup("#tbTeam").queryTableView();
        int rowCount = tbTeam.getItems().size();

        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        Team selectedTeam = (Team) tbTeam.getItems().get(rowCount - 1);
        clickOn(row);
        clickOn("#btnEliminar");
        assertFalse("Team deleted.", tbTeam.getItems().contains(selectedTeam));

    }
}
