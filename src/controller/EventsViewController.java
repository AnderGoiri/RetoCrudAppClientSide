/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GenericController.LOGGER;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Collection;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Event;
import model.Game;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import model.Organizer;
import model.User;

/**
 * FXML Controller class
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventsViewController extends GenericController {

    @FXML
    private Label lbVentana, lbBusqueda, lbNombre, lbJuego, lbLugar, lbONG, lbPremio, lbDonacion, lbAforo, lbFecha, lbError, lbEquipo;
    @FXML
    private ComboBox<String> cbBusqueda, cbJuego;
    @FXML
    private ComboBox<?> cbEquipo;
    @FXML
    private TextField tfNombre, tfLugar, tfONG, tfPremio, tfDonacion, tfAforo;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btnBuscar, btnLimpiar, btnCrear, btnModificar, btnEliminar, btnSalir, btnUnirse;
    @FXML
    private ImageView imgBuscar, imgLimpiar;
    @FXML
    private TableView<Event> tableViewEvents;
    @FXML
    private TableColumn<?, ?> columnNombre, columnJuego, columnLugar, columnFecha, columnAforo, columnONG, columnPremio, columnDonacion, columnGanador;

    private ObservableList<Event> eventsData;

    private Properties configFile = new Properties();

    private InputStream input;

    private String dateFormatPattern;

    private Collection<Game> games;
    private List<Organizer> allOrganizers;

    /**
     * Initializes the controller class.
     *
     * @param root
     * @param appUser
     */
    public void initStage(Parent root, User appUser) {
        try {
            Scene scene = new Scene(root);
            stage = new Stage();

            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("EVENTOS");
            stage.setResizable(false);

            //Set properties on showing
            btnUnirse.setDisable(true);
            btnBuscar.setDisable(true);
            btnCrear.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            cbEquipo.setDisable(true);

            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    "findEventsByOrganizer",
                    "findEventsByGame",
                    "findEventsWonByPlayer",
                    "findEventsWonByTeam",
                    "findEventsByONG"
            );
            cbBusqueda.setItems(namedQueriesList);
            cbBusqueda.setValue("Elegir criterio de b\u00FAsqueda");

            games = gameManager.getAllGames();
            ObservableList<String> gameNames = FXCollections.observableArrayList(
                    games.stream()
                            .map(Game::getName)
                            .collect(Collectors.toList())
            );
            cbJuego.setItems(gameNames);

            allOrganizers = organizerManager.findAll();
            ObservableList<Organizer> organizers = FXCollections.observableArrayList(
                    allOrganizers.stream()
                            .collect(Collectors.toList())
            );

            tableViewEvents.setEditable(false);
            lbError.setVisible(false);
            btnBuscar.setDefaultButton(true);

            // Load date pattern from configuration file
            input = getClass().getClassLoader().getResourceAsStream("config/parameters.properties");
            configFile.load(input);
            dateFormatPattern = configFile.getProperty("dateFormatPattern");

            //Setting Event TableView cell values
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnJuego.setCellValueFactory(new PropertyValueFactory<>("game"));
            columnLugar.setCellValueFactory(new PropertyValueFactory<>("location"));
            columnFecha.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnAforo.setCellValueFactory(new PropertyValueFactory<>("participantNum"));
            columnONG.setCellValueFactory(new PropertyValueFactory<>("ong"));
            columnPremio.setCellValueFactory(new PropertyValueFactory<>("prize"));
            columnDonacion.setCellValueFactory(new PropertyValueFactory<>("donation"));
            columnGanador.setCellValueFactory(new PropertyValueFactory<>("ganador"));

            Organizer organizer = organizerManager.find(appUser.getId().toString());

            switch (appUser.getUser_type()) {
                case "admin":
                    //columnFecha.setCellFactory(DatePickerCellEvent.forTableColumn(dateFormatPattern));
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    tableViewEvents.setItems(eventsData);
                    //Admin admin = adminManager.find(appUser.getId().toString());
                    break;
                case "organizer":
                    //columnFecha.setCellFactory(DatePickerCellEvent.forTableColumn(dateFormatPattern));
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    tableViewEvents.setItems(eventsData);
                    //Organizer organizer = organizerManager.find(appUser.getId().toString());
                    break;
                case "player":
                    //columnFecha.setCellFactory(DatePickerCellEvent.forTableColumn(dateFormatPattern));
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    tableViewEvents.setItems(eventsData);
                    //Player player = playerAdmin.find(appUser.getId().toString());
                    break;
                default:
                    System.out.println("No user type detected");
                    break;
            }

            // Set handlers
            stage.setOnCloseRequest(event -> super.handleCloseRequest(event));
            btnSalir.setOnAction(event -> super.handleBtnClose(event));
            btnLimpiar.setOnAction(event -> handleCleanRequest(event));
            btnCrear.setOnAction(event -> handleCreateEvent(event, organizer));
            btnModificar.setOnAction(event -> handleModifyEvent(event));
            btnEliminar.setOnAction(event -> handleDeleteEvent(event));

            // Set listeners
            /* 
            * The lambda expression (observable, oldValue, newValue) -> checkFormFields().
            * Observable: It is the object that emitted the event, in this case, the textProperty().
            * oldValue: It is the old value before the change occurred.
            * newValue: It is the new value after the change occurred.
             */
            tfNombre.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            tfLugar.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            dpFecha.valueProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            cbJuego.valueProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            tfONG.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            tfPremio.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            tfDonacion.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());
            tfAforo.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields());

            tableViewEvents.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    Event selectedEvent = tableViewEvents.getSelectionModel().getSelectedItem();
                    tfNombre.setText(selectedEvent.getName());
                    tfLugar.setText(selectedEvent.getLocation());
                    dpFecha.setValue(selectedEvent.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    cbJuego.setValue(selectedEvent.getGame().getName());
                    tfONG.setText(selectedEvent.getName());
                    tfPremio.setText(String.valueOf(selectedEvent.getPrize()));
                    tfDonacion.setText(String.valueOf(selectedEvent.getDonation()));
                    tfAforo.setText(String.valueOf(selectedEvent.getParticipantNum()));

                    btnModificar.setDisable(false);
                    btnEliminar.setDisable(false);
                } else {
                    btnModificar.setDisable(true);
                    btnEliminar.setDisable(true);
                }
            });

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido abrir la ventana:" + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    public void handleCleanRequest(ActionEvent event) {
        try {
            LOGGER.info("Limpiar button clicked.");
            tfNombre.clear();
            tfLugar.clear();
            tfONG.clear();
            tfPremio.clear();
            tfDonacion.clear();
            tfAforo.clear();
            dpFecha.getEditor().clear();
            cbBusqueda.getSelectionModel().clearSelection();
            cbJuego.getSelectionModel().clearSelection();
            cbEquipo.getSelectionModel().clearSelection();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error cleaning the form", ex);
        }
    }

    private void checkFormFields() {
        boolean allFieldsFilled = !tfNombre.getText().isEmpty()
                && !tfLugar.getText().isEmpty()
                && dpFecha.getValue() != null
                && cbJuego.getValue() != null
                && !tfONG.getText().isEmpty()
                && !tfPremio.getText().isEmpty()
                && !tfDonacion.getText().isEmpty()
                && !tfAforo.getText().isEmpty();
        btnCrear.setDisable(!allFieldsFilled);
    }

    private void handleCreateEvent(ActionEvent event, Organizer organizer) {
        try {
            Event newEvent = new Event();

            // Create the event with the info from the table
            newEvent.setName(tfNombre.getText());
            newEvent.setLocation(tfLugar.getText());
            newEvent.setOng(tfONG.getText());
            newEvent.setParticipantNum(Integer.parseInt(tfAforo.getText()));
            newEvent.setDate(Date.from(dpFecha.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newEvent.setPrize(Float.parseFloat(tfPremio.getText()));
            newEvent.setDonation(Float.parseFloat(tfDonacion.getText()));
            newEvent.setOrganizer(organizer);
            /*
            To establish a Game for the event, we take the game collection declared in this controller.
            Then create a stream filetered by the value of the Game ComboBox.
            The result is always going to be one Game.
            This stream is converted to a a list. To retrieve the game we use 
            the get() method for the first position of the list. 
             */
            newEvent.setGame(games.stream()
                    .filter((g -> g.getName()
                    .equals(cbJuego.getValue())))
                    .collect(Collectors.toList())
                    .get(0));
            eventManager.createEvent(newEvent);
            eventsData.clear();
            eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
            tableViewEvents.setItems(eventsData);
            tableViewEvents.refresh();

            lbError.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.getMessage());
            lbError.setText("Ha ocurrido un error al crear un evento");
            lbError.setVisible(true);
        }
    }

    private void handleModifyEvent(ActionEvent event) {
        try {
            lbError.setVisible(false);

            // Get the selected Event in the table
            Event selectedEvent = tableViewEvents.getSelectionModel().getSelectedItem();

            if (selectedEvent != null) {
                selectedEvent.setName(tfNombre.getText());
                selectedEvent.setName(tfNombre.getText());
                selectedEvent.setLocation(tfLugar.getText());
                selectedEvent.setOng(tfONG.getText());
                selectedEvent.setParticipantNum(tfAforo.getAnchor());
                selectedEvent.setDate(Date.from(dpFecha.getValue()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                selectedEvent.setPrize(Float.NaN);
                selectedEvent.setDonation(Float.NaN);
                //newEvent.setGame(cbJuego.getValue());
                //newEvent.setPlayerevents(playerevents);
                //newEvent.setTeamevents(teamevents);

                LOGGER.info("Event modified");
            } else {
                LOGGER.warning("No Event selected");
                lbError.setText("No Event selected");
                lbError.setVisible(true);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            lbError.setText("Ha ocurrido un error al crear un evento");
            lbError.setVisible(true);
        }
    }

    private void handleDeleteEvent(ActionEvent event) {
        try {
            lbError.setVisible(false);
            Event selectedEvent = tableViewEvents.getSelectionModel().getSelectedItem();

            if (selectedEvent != null) {
                // Call the method to delete the event in the database
                eventManager.deleteEvent(selectedEvent);

                // Deleting the event from the TableView list
                int selectedIndex = tableViewEvents.getSelectionModel().getSelectedIndex();
                eventsData.remove(selectedIndex);

                // Refresh the table with the modified data
                tableViewEvents.refresh();

                LOGGER.info("Event deleted");
            }
        } catch (Exception e) {
            LOGGER.severe("Error deleting the team: " + e.getMessage());
            lbError.setText("An error occured while deleting the event");
            lbError.setVisible(true);
        }
    }
}
