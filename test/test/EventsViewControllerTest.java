package test;

import controller.EventsViewController;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Event;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventsViewControllerTest {

    private TextField tfNombre, tfLugar, tfONG, tfPremio, tfDonacion, tfAforo;
    private DatePicker dpFecha;

    public EventsViewControllerTest() {
    }

    /**
     * Test of initStage method, of class EventsViewController.
     */
    @Test
    public void testInitStage() {
        System.out.println("initStage");
        Parent root = null;
        EventsViewController instance = new EventsViewController();
        instance.initStage(root);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testHandleCreateEvent() {
        System.out.println("handleCreateEvent");

        // Mocking necessary components
        Parent root = null; // You may need to initialize this with a mock or stub
        EventsViewController instance = new EventsViewController();
        instance.initStage(root);

        // Prepare input data
        tfNombre = new TextField("Evento de prueba" + new Random().nextInt());
        tfLugar = new TextField("Lugar de prueba" + new Random().nextInt());
        /*

        // Set input data into the controller's text fields and combo box
        instance.tfNombre = tfNombre;
        instance.tfLugar = tfLugar;
        instance.cbJuego = cbJuego;
        instance.dpFecha = dpFecha;
        instance.tfONG = tfONG;
        instance.tfPremio = tfPremio;
        instance.tfDonacion = tfDonacion;
        instance.tfAforo = tfAforo;

        // Execute the method to create the event
        instance.handleCreateEvent(null);

        // Check if the event is created and added to the events list
        ObservableList<Event> eventsData = instance.eventsData;
        assertNotNull(eventsData);
        assertFalse(eventsData.isEmpty()); // Check if events list is not empty

        // Get the last added event and check its attributes
        Event createdEvent = eventsData.get(eventsData.size() - 1);
        assertEquals("Evento de prueba", createdEvent.getName());
        assertEquals("Lugar de prueba", createdEvent.getLocation());

        // Optionally, you can also check if the event is added to the TableView
        TableView<Event> tableViewEvents = instance.tableViewEvents;
        assertNotNull(tableViewEvents);
        ObservableList<TableColumn<Event, ?>> columns = tableViewEvents.getColumns();
        assertFalse(columns.isEmpty()); // Check if TableView has columns

        // Optionally, you can check if the TableView contains the created event
        assertTrue(tableViewEvents.getItems().contains(createdEvent));
         */
    }

    /**
     * Test of handleCleanRequest method, of class EventsViewController.
     */
    @Test
    public void testHandleCleanRequest() {
        System.out.println("handleCleanRequest");
        ActionEvent event = null;
        EventsViewController instance = new EventsViewController();
        instance.handleCleanRequest(event);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
