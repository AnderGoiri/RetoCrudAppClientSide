package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;
import model.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import main.RetoCrudAppClient;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
public class EventsViewControllerCrudTest extends ApplicationTest {

    private static Event event;
    @FXML
    private TableView tableViewEvents;

    @BeforeClass
    public static void setUpClass() throws Exception {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(RetoCrudAppClient.class);
    }

    @Test
    public void test1_InitStage() {
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

    //@Ignore
    @Test
    public void test2_CreateEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        clickOn("#tfNombre");
        String evento = "event" + String.valueOf(new Random().nextInt(99) + 1);
        write(evento);
        clickOn("#cbJuego");
        press(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn("#tfLugar");
        String lugar = "lugar" + String.valueOf(new Random().nextInt(99) + 1);
        write(lugar);
        clickOn("#tfONG");
        String ong = "ong" + String.valueOf(new Random().nextInt(99) + 1);
        write(ong);
        clickOn("#dpFecha");
        write(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        clickOn("#tfPremio");
        int premio = new Random().nextInt(1801) + 200;
        write(String.valueOf(premio));
        clickOn("#tfDonacion");
        double donacion = (new Random().nextInt(89) + 10) / 100.0;
        write(String.format(Locale.US, "%.2f", donacion));
        clickOn("#tfAforo");
        int aforo = (new Random().nextInt(43) + 8) * 2;
        write(String.valueOf(aforo));
        clickOn("#btnCrear");
        assertEquals("The row has not been added!!!", rowCount + 1, tableViewEvents.getItems().size());
        List<Event> events = tableViewEvents.getItems();
        assertEquals("The event has been added!",
                events.stream()
                        .filter(e -> e.getName().equals(evento) && e.getLocation().equals(lugar)).count(), 1);
        event = events.stream()
                .filter(e -> e.getName().equals(evento) && e.getLocation().equals(lugar)).collect(Collectors.toList()).get(0);
        sleep(500);
    }

    //@Ignore
    @Test
    public void test3_SearchAllEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        clickOn("#btnLimpiar");
        clickOn("#cbBusqueda");
        clickOn("Buscar todos los eventos");
        clickOn("#btnBuscar");
        assertEquals("All Events searched succedfully",
                rowCount, tableViewEvents.getItems().size());
    }

    @Test
    public void test4_ModifyEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        clickOn(row);

        Event selectedEvent = (Event) tableViewEvents.getSelectionModel().getSelectedItem();
        int selectedIndex = tableViewEvents.getSelectionModel().getSelectedIndex();

        Event modifiedEvent = new Event();

        modifiedEvent.setName(selectedEvent.getName() + new Random().nextInt(99));
        doubleClickOn("#tfNombre");
        eraseText(selectedEvent.getName().length());
        write(modifiedEvent.getName());

        modifiedEvent.setLocation(selectedEvent.getLocation() + new Random().nextInt(99));
        doubleClickOn("#tfLugar");
        eraseText(selectedEvent.getLocation().length());
        write(modifiedEvent.getLocation());

        LocalDate newDate = LocalDate.now().plusDays(1);
        String formattedDate = newDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        clickOn("#dpFecha");
        write(formattedDate);

        clickOn("#btnModificar");

        // Validate modification by checking if the event details have been updated in the table
        Event updatedEvent = (Event) tableViewEvents.getItems().get(selectedIndex);
        assertEquals(modifiedEvent.getName(), updatedEvent.getName());
        assertEquals(modifiedEvent.getLocation(), updatedEvent.getLocation());
    }

    //@Ignore
    @Test
    public void test5_DeleteEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        Event eventToDelete = (Event) tableViewEvents.getItems().get(rowCount - 1);
        clickOn(row);
        clickOn("#btnEliminar");
        sleep(500);
        clickOn("Confirmar");
        assertFalse("El evento no se ha eliminado correctamente",
                tableViewEvents.getItems().contains(eventToDelete));
    }

}
