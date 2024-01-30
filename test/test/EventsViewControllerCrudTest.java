package test;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.Event;
import javafx.fxml.FXML;
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

    private TextField tfNombre, tfLugar, tfONG, tfPremio, tfDonacion, tfAforo;
    private DatePicker dpFecha;
    @FXML
    private TableView tableViewEvents;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Locale.setDefault(Locale.US);
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
        clickOn("#tfPremio");
        int premio = new Random().nextInt(1801) + 200;
        write(String.valueOf(premio));
        clickOn("#tfDonacion");
        double donacion = (new Random().nextInt(89) + 10) / 100.0;
        DecimalFormat df = new DecimalFormat("#.##");
        write(df.format(donacion));
        clickOn("#tfAforo");
        int aforo = (new Random().nextInt(43) + 8) * 2;
        write(String.valueOf(aforo));
        clickOn("#dpFecha");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomEpochDay);
        write(randomDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        clickOn("#btnCrear");
        assertEquals("The row has not been added!!!", rowCount + 1, tableViewEvents.getItems().size());
        List<Event> events = tableViewEvents.getItems();
        assertEquals("The event has been added!",
                events.stream()
                        .filter(e -> e.getName().equals(evento)).count(), 1);
    }

    public void test3_SearchEvent() {

    }

    public void test4_ModifyEvent() {

    }

    public void test5_DeleteEvent() {

    }

}
