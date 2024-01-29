/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import main.RetoCrudAppClient;
import model.Game;
import model.PVPType;
import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import org.testfx.robot.ClickRobot;

/**
 *
 * @author Andoni Sanz
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGameWindowController extends ApplicationTest {

    private TableView<Game> tbGames;
    private TableColumn<Game, String> txtName;

    /**
     * Sets up the TestFX environment for the entire test class. This method is
     * annotated with {@code @BeforeClass}, indicating that it runs once before
     * any tests in the class. It registers the primary stage and sets up the
     * application using TestFX.
     *
     * @throws TimeoutException If a timeout occurs during the setup.
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(RetoCrudAppClient.class);
    }

    @Test
    public void testA_createGame() {
        //Changing windows
        tbGames = lookup("#tbGames").query();
        
        Integer gameCount = tbGames.getItems().size();
        clickOn("#btnAddRow");
        
        ObservableList<Game> games = tbGames.getItems();
        
        assertEquals((Object)(gameCount+1), (Object)games.size());
        
        assertEquals(games.get(games.size()-1).getName(), "Default Name");
        assertEquals(games.get(games.size()-1).getGenre(), "Default Genre");
        assertEquals(games.get(games.size()-1).getPlatform(), "Default Platform");
        assertEquals(games.get(games.size()-1).getPVPType(), PVPType.TEAM_BASED_5V5);
        assertEquals(games.get(games.size()-1).getReleaseDate(), null);
        
        /*Node tbColName = lookup("#tbcolName").nth(2).query();

        if (tbColName instanceof Label) {
            String labelText = ((Label) tbColName).getText();
            System.out.println("Text in the cell: " + labelText);
        } else {
            // Manejar otros tipos de nodos según sea necesario
            System.out.println("Unexpected node type in the cell");
        }

        clickOn(tbColName);
        verifyThat(".tbcolName", hasText("Default Name"));*/
    }
    
    @Test
    public void testA_updateGame() {
        
        Node tbColName = lookup("#tbcolName").nth(3).query();
        Node tbColGenre = lookup("#tbcolGenre").nth(3).query();
        Node tbColPlatform = lookup("#tbcolPlatform").nth(3).query();
        Node tbColPVPType = lookup("#tbcolPVPType").nth(3).query();
        Node tbColReleaseDate = lookup("#tbcolReleaseDate").nth(3).query();
        
        doubleClickOn(tbColName);
        write("Valorant");
        doubleClickOn(tbColGenre);
        write("Shooter");
        doubleClickOn(tbColPlatform);
        write("PC");
        /*doubleClickOn(tbColPVPType);
        write(PVPType.TEAM_BASED_5V5);*/
        doubleClickOn(tbColReleaseDate);
        write("01/01/2024");
    }
    
}
