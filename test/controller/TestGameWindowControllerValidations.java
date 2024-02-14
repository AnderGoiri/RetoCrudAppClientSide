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
import javafx.collections.ObservableList;
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
public class TestGameWindowControllerValidations extends ApplicationTest {

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

    @Test
    public void testA_validateMaxCharException() {
        tbGames = lookup("#tbGames").query();

        Integer gameCount = 0;

        if (tbGames != null) {
            gameCount = tbGames.getItems().size();
        }

        //MAX CHAR EXCEPTION
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 100;
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

        Label lblError = (Label) lookup("#lblError").query();

        verifyThat("#lblError", isVisible());
        assertEquals("LLegaste al máximo de carácteres", lblError.getText());
    }

    @Test
    public void testB_validateWrongFormatException() {
        tbGames = lookup("#tbGames").query();

        Integer gameCount = 0;

        if (tbGames != null) {
            gameCount = tbGames.getItems().size();
        }

        Node tbColName = lookup("#tbcolName").nth(gameCount).query();
        Node tbColGenre = lookup("#tbcolGenre").nth(gameCount).query();
        Node tbColPlatform = lookup("#tbcolPlatform").nth(gameCount).query();
        Node tbColPVPType = lookup("#tbcolPVPType").nth(gameCount).query();
        Node tbColReleaseDate = lookup("#tbcolReleaseDate").nth(gameCount).query();

        doubleClickOn(tbColName);
        eraseText(10);

        write("t3st V4l1d4t10n");
        press(KeyCode.ENTER);

        Label lblError = (Label) lookup("#lblError").query();

        verifyThat("#lblError", isVisible());
        assertEquals("En el nombre solo se permiten escribir letras", lblError.getText());

    }

    @Test
    public void testC_validateNameAlreadyExistsException() {

        tbGames = lookup("#tbGames").query();
        Integer gameCount = tbGames.getItems().size();
        clickOn("#btnAddRow");

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

        doubleClickOn(tbColName);
        eraseText(10);
        write(generatedName);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        //create other game and add same name
        clickOn("#btnAddRow");

        tbColName = lookup("#tbcolName").nth(gameCount + 1).query();

        doubleClickOn(tbColName);
        eraseText(10);
        write(generatedName);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        Label lblError = (Label) lookup("#lblError").query();

        verifyThat("#lblError", isVisible());
        assertEquals("El nombre de este juego ya está registrado", lblError.getText());
    }
}
