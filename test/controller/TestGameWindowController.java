/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static java.lang.Math.random;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import main.Application;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import main.GameStart;
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
        FxToolkit.setupApplication(GameStart.class);
    }

    /*@Test
    public void testA_createGame() {
        //Changing windows
        tbGames = lookup("#tbGames").query();

        Integer gameCount = 0;

        if (tbGames != null) {
            gameCount = tbGames.getItems().size();
        }

        clickOn("#btnAddRow");

        ObservableList<Game> games = tbGames.getItems();

        assertEquals((Object) (gameCount + 1), (Object) games.size());

        assertEquals("Default", games.get(games.size() - 1).getName());
        assertEquals("Default", games.get(games.size() - 1).getGenre());
        assertEquals("Default", games.get(games.size() - 1).getPlatform());
        assertEquals(PVPType.TEAM_BASED_5V5, games.get(games.size() - 1).getPVPType());
        assertEquals(null, games.get(games.size() - 1).getReleaseDate());
    }*/

 /*@Test
    public void testB_updateGame() {

        tbGames = lookup("#tbGames").query();
        Integer gameCount = tbGames.getItems().size();

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedName = buffer.toString();

        Node tbColName = lookup("#tbcolName").nth(gameCount).query();
        Node tbColGenre = lookup("#tbcolGenre").nth(gameCount).query();
        Node tbColPlatform = lookup("#tbcolPlatform").nth(gameCount).query();
        Node tbColPVPType = lookup("#tbcolPVPType").nth(gameCount).query();
        Node tbColReleaseDate = lookup("#tbcolReleaseDate").nth(gameCount).query();

        doubleClickOn(tbColName);
        eraseText(10);

        write(generatedName);
        press(KeyCode.ENTER);

        doubleClickOn(tbColGenre);
        eraseText(10);
        write("Shooter");
        release(KeyCode.ENTER); //release key is used because the robot seems to bug when pressing it more than once
        press(KeyCode.ENTER);

        doubleClickOn(tbColPlatform);
        eraseText(10);
        write("PC");
        release(KeyCode.ENTER);
        press(KeyCode.ENTER);

        doubleClickOn(tbColPVPType);
        clickOn(tbColPVPType);
        press(KeyCode.DOWN);
        press(KeyCode.ENTER);

        doubleClickOn(tbColReleaseDate);
        clickOn(tbColReleaseDate);
        write("01/01/2024");
        release(KeyCode.ENTER);
        press(KeyCode.ENTER);

        ObservableList<Game> games = tbGames.getItems();
        assertEquals(generatedName, games.get(games.size() - 1).getName());
        assertEquals("Shooter", games.get(games.size() - 1).getGenre());
        assertEquals("PC", games.get(games.size() - 1).getPlatform());
        assertEquals(PVPType.TEAM_BASED_3V3, games.get(games.size() - 1).getPVPType());
        assertEquals("Mon Jan 01 00:00:00 CET 2024", games.get(games.size() - 1).getReleaseDate().toString());
    }*/
    @Test
    public void testC_1_SearchByName() {

        Node cmbSearch = lookup("#cmbSearch").query();
        Node btnSearch = lookup("#btnSearch").query();
        Node tfSearchData = lookup("#tfSearchData").query();

        clickOn(cmbSearch);
        push(KeyCode.DOWN);
        clickOn(tfSearchData);
        write("Valorant");
        clickOn(btnSearch);

        TableView tbGamesSearch = lookup("#tbGames").query();
        
        ObservableList<Game> games = tbGamesSearch.getItems();
        FilteredList<Game> filteredList = new FilteredList<>(games);
        
        TableView tbGamesFilteredSearch = new TableView();          
        tbGamesFilteredSearch.setItems(filteredList);
        
        clickOn(cmbSearch);
        push(KeyCode.UP);
        clickOn(btnSearch);
        
        // to filter
        filteredList.setPredicate(new Predicate<Game>() {
            public boolean test(Game t) {
                if(t.getName().equalsIgnoreCase("Valorant"))
                    return true;
                else
                    return false;
            }
        }
        );
        
        ObservableList<Game> gamesFiltered = tbGamesFilteredSearch.getItems();
        
        
        ((TextField)tfSearchData).clear();
                
        assertEquals(games, gamesFiltered);

        //Integer gameCountPostDelete = tbGames.getItems().size();
        //assertEquals(gameCountPreDelete.toString(), String.valueOf(gameCountPostDelete + 1));
    }
    
    @Test
    public void testC_2_SearchByGenre() {

        //tbGames = lookup("#tbGames").query();
        Node cmbSearch = lookup("#cmbSearch").query();
        Node btnSearch = lookup("#btnSearch").query();
        Node tfSearchData = lookup("#tfSearchData").query();
        //Integer gameCount = tbGames.getItems().size();

        clickOn(cmbSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        clickOn(tfSearchData);
        write("shooter");
        clickOn(btnSearch);

        TableView tbGamesSearch = lookup("#tbGames").query();
                
        clickOn(cmbSearch);
        push(KeyCode.UP);
        push(KeyCode.UP);
        clickOn(btnSearch);
        
        TableView tbGamesFilteredSearch = lookup("#tbGames").query();
        ObservableList<Game> games = tbGamesFilteredSearch.getItems();
        FilteredList<Game> filteredList = new FilteredList<>(games);
        
        tbGamesFilteredSearch.setItems(filteredList);

        // to filter
        filteredList.setPredicate(new Predicate<Game>() {
            public boolean test(Game t) {
                if(t.getName().equalsIgnoreCase("shooter"))
                    return true;
                else
                    return false;
            }
        }
        );
        
        ((TextField)tfSearchData).clear();
        
        assertEquals(tbGamesSearch, tbGamesFilteredSearch);
    }

    @Test
    public void testC_3_SearchByPlatform() {

        //tbGames = lookup("#tbGames").query();
        Node cmbSearch = lookup("#cmbSearch").query();
        Node btnSearch = lookup("#btnSearch").query();
        Node tfSearchData = lookup("#tfSearchData").query();
        //Integer gameCount = tbGames.getItems().size();

        clickOn(cmbSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        clickOn(tfSearchData);
        write("pc");
        clickOn(btnSearch);

        TableView tbGamesSearch = lookup("#tbGames").query();
                
        clickOn(cmbSearch);
        push(KeyCode.UP);
        push(KeyCode.UP);
        push(KeyCode.UP);
        clickOn(btnSearch);
        
        TableView tbGamesFilteredSearch = lookup("#tbGames").query();
        ObservableList<Game> games = tbGamesFilteredSearch.getItems();
        FilteredList<Game> filteredList = new FilteredList<>(games);
        
        tbGamesFilteredSearch.setItems(filteredList);

        // to filter
        filteredList.setPredicate(new Predicate<Game>() {
            public boolean test(Game t) {
                if(t.getName().equalsIgnoreCase("pc"))
                    return true;
                else
                    return false;
            }
        }
        );
        
        ((TextField)tfSearchData).clear();
        
        assertEquals(tbGamesSearch, tbGamesFilteredSearch);
    }
    
    @Test
    public void testD_deleteGame() {

        Node btnSearch = lookup("#btnSearch").query();
        clickOn(btnSearch);
        
        tbGames = lookup("#tbGames").query();
        Integer gameCountPreDelete = tbGames.getItems().size();

        Node tbColDeselect = lookup("#tbcolId").nth(gameCountPreDelete - 1).query();
        clickOn(tbColDeselect);
        Node tbCol = lookup("#tbcolId").nth(gameCountPreDelete).query();
        clickOn(tbCol);

        press(KeyCode.DELETE);

        sleep(20);

        Integer gameCountPostDelete = tbGames.getItems().size();

        assertEquals(gameCountPreDelete.toString(), String.valueOf(gameCountPostDelete + 1));
    }
}
