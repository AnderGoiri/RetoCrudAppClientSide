/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import main.TeamStart;
import model.Team;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
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
public class TeamWindowControllerTest extends ApplicationTest {
    
    @FXML
    private TableView<Team> tbTeam;
 
    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TeamStart.class);
    }

    @Test
    public void test1InitStage(){
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
        clickOn("#btnCrear");
        assertEquals("The row has not been added.", rowCount + 1, tbTeam.getItems().size());
        List<Team> teams = tbTeam.getItems();
        assertEquals("The team has not been added.", teams.stream().filter(t -> t.getName().equals(team) && t.getCoach().equals(coach)).count(), 1);
        
        sleep(500);
    }
    
    
    @Test
    public void test3FindTeamByName() {
       tbTeam = lookup("#tableViewEvents").queryTableView();
       int rowCount = tbTeam.getItems().size();
       clickOn("#cmbBusqueda");
       clickOn("Por nombre");
    }
    
    @Test
    public void test4ModifyTeam() {
        
    }

    @Test
    public void test5DeleteTeam() {
       
    }
}
