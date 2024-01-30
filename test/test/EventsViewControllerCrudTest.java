package test;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import main.RetoCrudAppClient;
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
public class EventsViewControllerCrudTest extends ApplicationTest {

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
        sleep(500);
    }

    @Test
    public void test3_SearchEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        clickOn("#btnLimpiar");
        clickOn("#cbBusqueda");
        clickOn("Buscar todos los eventos");
        clickOn("#btnBuscar");
        assertEquals("All Events searched succedfully",
                rowCount, tableViewEvents.getItems().size());
        /*
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Event selectedEvent = (Event) tableViewEvents.getSelectionModel()
                .getSelectedItem();
        clickOn("#btnLimpiar");
        clickOn("#cbBusqueda");
        clickOn("Buscar eventos por ONG");
        clickOn("#tfONG");
        String selectedONG = selectedEvent.getOng();
        write(String.valueOf(selectedONG));
        clickOn("#btnBuscar");
        rowCount = tableViewEvents.getItems().size();
        List<Event> events = tableViewEvents.getItems();
        assertEquals("The search was successful",
                rowCount,
                events.stream()
                        .filter(event -> event.getOng().equals(selectedEvent.getOng()))
                        .count());
         */
    }

    @Test
    public void test4_ModifyEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();

        // Seleccionar la última fila para modificar el evento
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        clickOn(row);

        // Modificar los campos del evento
        String newEventName = "New Event Name";
        clickOn("#tfNombre");
        write(newEventName);

        String newLocation = "New Location";
        clickOn("#tfLugar");
        write(newLocation);

        // Simular la selección de una nueva fecha
        clickOn("#dpFecha");
        write(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        String newOng = "New ONG";
        clickOn("#tfONG");
        write(newOng);

        int newPrize = 1000; // Nuevo premio
        clickOn("#tfPremio");
        write(String.valueOf(newPrize));

        double newDonation = 0.5; // Nueva donación
        clickOn("#tfDonacion");
        write(String.format(Locale.US, "%.2f", newDonation));

        int newCapacity = 50; // Nueva capacidad
        clickOn("#tfAforo");
        write(String.valueOf(newCapacity));

        // Guardar la modificación
        clickOn("#btnModificar");

        // Comprobar que el evento se ha modificado correctamente
        List<Event> events = tableViewEvents.getItems();
        Event modifiedEvent = events.get(rowCount - 1);
        assertEquals("The event name has not been modified", newEventName, modifiedEvent.getName());
        assertEquals("The event location has not been modified", newLocation, modifiedEvent.getLocation());
        assertEquals("The event date has not been modified", LocalDate.now().plusDays(1), modifiedEvent.getDate());
        assertEquals("The event ONG has not been modified", newOng, modifiedEvent.getOng());
        assertEquals("The event prize has not been modified", newPrize, modifiedEvent.getPrize());
        assertEquals("The event donation has not been modified", newDonation, modifiedEvent.getDonation(), 0.01);
        assertEquals("The event capacity has not been modified", newCapacity, modifiedEvent.getCapacity());
    }

    @Test
    public void test5_DeleteEvent() {
        tableViewEvents = lookup("#tableViewEvents").queryTableView();
        int rowCount = tableViewEvents.getItems().size();
        Node row = lookup(".table-row-cell").nth(rowCount - 1).query();
        Event eventToDelete = (Event) tableViewEvents.getItems().get(rowCount - 1);
        clickOn(row);
        clickOn("#btnEliminar");
        assertFalse("El evento no se ha eliminado correctamente",
                tableViewEvents.getItems().contains(eventToDelete));
    }

}
