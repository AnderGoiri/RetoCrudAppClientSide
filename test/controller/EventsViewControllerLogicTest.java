/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import main.Application;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
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
 * @author Ander Goirigolzarri Iturburu
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventsViewControllerLogicTest extends ApplicationTest {

    @FXML
    private TableView tableViewEvents;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Register the primary stage of the JavaFX application.
        FxToolkit.registerPrimaryStage();
        // Setup and launch the JavaFX application.
        FxToolkit.setupApplication(Application.class);
    }

    @Test
    public void test0_InitStage() {
        clickOn("#txtEmail");
        write("organizer");
        clickOn("#pwdPassword");
        write("Abcd*1234");
        clickOn("#loginButton");
        verifyThat("#hBoxMenu", isVisible());
        verifyThat("#tfNombre", hasText(""));
        verifyThat("#tfLugar", hasText(""));
        verifyThat("#tfONG", hasText(""));
        verifyThat("#btnCrear", isDisabled());
        verifyThat("#btnModificar", isDisabled());
        verifyThat("#btnEliminar", isDisabled());
        verifyThat("#btnSalir", isEnabled());
        verifyThat("#tableViewEvents", isVisible());
    }

    @Test
    public void test1_DuplicateEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        clickOn(row);
        clickOn("#btnCrear");
        verifyThat("#lbError", isVisible());
    }
}
