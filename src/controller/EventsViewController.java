/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GenericController.LOGGER;
import exceptions.EmptyGameAlreadyAddedException;
import exceptions.EventAlreadyExistsException;
import exceptions.NumericFormatException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Comparator;
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
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javax.ejb.CreateException;
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

    private MenuItem createItem;
    private MenuItem readItem;
    private MenuItem updateItem;
    private MenuItem deleteItem;
    private MenuItem teamsItem;
    private MenuItem gamesItem;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root, User appUser) {
        try {
            Scene scene = new Scene(root);
            stage = new Stage();

            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("eSportsHub - EVENTOS");
            stage.setResizable(false);

            //Set properties on showing
            btnUnirse.setDisable(true);
            btnBuscar.setDisable(true);
            btnCrear.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);
            cbEquipo.setDisable(true);

            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    //"findEventsByOrganizer",
                    "Buscar todos los eventos", //findAllEvents
                    "Buscar eventos por Juego", //findEventsByGame
                    //"findEventsWonByPlayer",
                    //"findEventsWonByTeam",
                    "Buscar eventos por ONG" //findEventsByONG
            );
            cbBusqueda.setItems(namedQueriesList);
            cbBusqueda.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    btnBuscar.setDisable(false);
                }
            });

            games = gameManager.getAllGames();
            ObservableList<String> gameNames = FXCollections.observableArrayList(
                    games.stream()
                            .map(Game::getName)
                            .collect(Collectors.toList())
            );
            cbJuego.setItems(gameNames);

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

            switch (appUser.getUser_type()) {
                case "organizer":
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    //eventsData = FXCollections.observableArrayList(eventManager.findEventsByOrganizer(appUser.getName()));
                    tableViewEvents.setItems(eventsData);
                    break;
                case "admin":
                    //mostrar los eventos de los juegos creados por él
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    tableViewEvents.setItems(eventsData);
                    break;
                case "player":
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    // Sort eventsData by date in descending order
                    eventsData.sort(Comparator.comparing(Event::getDate).reversed());
                    tableViewEvents.setItems(eventsData);
                    break;
                default:
                    eventsData = FXCollections.observableArrayList(eventManager.findAllEvents());
                    tableViewEvents.setItems(eventsData);
                    break;
            }

            // Set handlers
            stage.setOnCloseRequest(event -> super.handleCloseRequest(event));
            btnSalir.setOnAction(event -> super.handleBtnClose(event));
            btnBuscar.setOnAction(event -> handleSearchRequest(event));
            btnLimpiar.setOnAction(event -> handleCleanRequest(event));
            btnCrear.setOnAction(event -> handleCreateEvent(event));
            btnModificar.setOnAction(event -> handleModifyEvent(event));
            btnEliminar.setOnAction(event -> handleDeleteEvent(event));

            // Set listeners
            /* 
            * The lambda expression (observable, oldValue, newValue) -> checkFormFields().
            * Observable: It is the object that emitted the event, in this case, the textProperty().
            * oldValue: It is the old value before the change occurred.
            * newValue: It is the new value after the change occurred.
             */
            tfNombre.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            tfLugar.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            dpFecha.valueProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            cbJuego.valueProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            tfONG.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            tfPremio.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            tfDonacion.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));
            tfAforo.textProperty().addListener((observable, oldValue, newValue) -> checkFormFields(appUser));

            tableViewEvents.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                Event selectedEvent = tableViewEvents.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    tfNombre.setText(selectedEvent.getName());
                    tfLugar.setText(selectedEvent.getLocation());
                    dpFecha.setValue(selectedEvent.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    cbJuego.setValue(selectedEvent.getGame().getName());
                    tfONG.setText(selectedEvent.getOng());
                    tfPremio.setText(String.valueOf(selectedEvent.getPrize()));
                    tfDonacion.setText(String.valueOf(selectedEvent.getDonation()));
                    tfAforo.setText(String.valueOf(selectedEvent.getParticipantNum()));
                    if (appUser.getUser_type().equals("player")) {
                        btnUnirse.setDisable(false);
                    }
                    if (appUser.getUser_type().equals("organizer")) {
                        btnModificar.setDisable(false);
                        btnEliminar.setDisable(false);
                    }
                } else {
                    btnModificar.setDisable(true);
                    btnEliminar.setDisable(true);
                }
            });
            /*
            // Create a context menu
            ContextMenu contextMenu = new ContextMenu();

            // CRUD options
            createItem = new MenuItem("    Crear Evento");
            updateItem = new MenuItem("    Modificar Evento");
            deleteItem = new MenuItem("    Eliminar Evento");
            teamsItem = new MenuItem("    Ir a Equipos");
            gamesItem = new MenuItem("    Ir a Juegos");

            // Separator
            SeparatorMenuItem separator = new SeparatorMenuItem();

            // Add actions to CRUD options
            createItem.setOnAction(event -> handleCreateEvent(event));
            updateItem.setOnAction(event -> handleModifyEvent(event));
            deleteItem.setOnAction(event -> handleDeleteEvent(event));

            // Add Navigation
            teamsItem.setOnAction(e -> System.out.println("Teams action"));
            gamesItem.setOnAction(e -> System.out.println("Games action"));

            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(createItem, readItem, deleteItem, separator, teamsItem, gamesItem);

            // Attach the context menu to the root pane
            root.setOnContextMenuRequested(event
                    -> contextMenu.show(root, event.getScreenX(), event.getScreenY()));
             */
            stage.show();
        } catch (Exception e) {
            //  e.printStackTrace();
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
            btnBuscar.setDisable(true);

            eventsData.clear();
            eventsData.addAll(eventManager.findAllEvents());
            tableViewEvents.refresh();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error cleaning the form", ex);
        }
    }

    private void checkFormFields(User appUser) {
        // Check if all input fields are filled
        boolean allFieldsFilled = !tfNombre.getText().isEmpty() // Check if the name field is not empty
                && !tfLugar.getText().isEmpty() // Check if the location field is not empty
                && dpFecha.getValue() != null // Check if a date is selected
                && cbJuego.getValue() != null // Check if a game is selected
                && !tfONG.getText().isEmpty() // Check if the NGO field is not empty
                && !tfPremio.getText().isEmpty() // Check if the prize field is not empty
                && !tfDonacion.getText().isEmpty() // Check if the donation field is not empty
                && !tfAforo.getText().isEmpty(); // Check if the participant number field is not empty
        // Enable or disable the create button based on the validation result
        if (appUser.getUser_type().equals("organizer")) {
            btnCrear.setDisable(!allFieldsFilled);
        } else {
            btnCrear.setDisable(true); // Disable the button for non-organizer users
        }
        /*
        // Enable or disable the corresponding context menu items based on the validation result
        createItem.setDisable(!btnCrear.isDisabled());
        updateItem.setDisable(!btnModificar.isDisabled());
        deleteItem.setDisable(!btnEliminar.isDisabled());
         */
    }

    private void handleCreateEvent(ActionEvent event) {
        try {
            String patternNaturalPositiveNumber = "\\d+";
            // Regular expression to validate that the value is a number between 0 and 1, with two decimal places maximum
            String donationFormat = "^(0(?:\\.\\d{1,2})?|1(?:\\.0{1,2})?)$";
            Event newEvent = new Event();

            // Create the event with the info from the table
            newEvent.setName(tfNombre.getText());
            newEvent.setLocation(tfLugar.getText());
            newEvent.setOng(tfONG.getText());
            if (tfAforo.getText().matches(patternNaturalPositiveNumber)) {
                newEvent.setParticipantNum(Integer.parseInt(tfAforo.getText()));
            } else {
                throw new NumericFormatException();
            }
            newEvent.setDate(Date.from(dpFecha.getValue()
                    .atStartOfDay(ZoneId.systemDefault()).toInstant()));
            if (tfPremio.getText().matches(patternNaturalPositiveNumber)) {
                newEvent.setPrize(Float.parseFloat(tfPremio.getText()));
            } else {
                throw new NumericFormatException();
            }
            // Check if the Donation value matches the regular expression
            if (tfDonacion.getText().matches(donationFormat)) {
                newEvent.setDonation(Float.parseFloat(tfDonacion.getText()));
            } else {
                throw new NumericFormatException();
            }
            newEvent.setDonation(Float.parseFloat(tfDonacion.getText()));
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
                selectedEvent.setParticipantNum(Integer.parseInt(tfAforo.getText()));
                selectedEvent.setDate(Date.from(dpFecha.getValue()
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()));
                selectedEvent.setPrize(Float.parseFloat(tfPremio.getText()));
                selectedEvent.setDonation(Float.parseFloat(tfDonacion.getText()));
                selectedEvent.setGame(games.stream()
                        .filter((g -> g.getName()
                        .equals(cbJuego.getValue())))
                        .collect(Collectors.toList())
                        .get(0));

                eventManager.modifyEvent(selectedEvent);
                LOGGER.info("Event modified");
                eventsData.clear();
                eventsData.addAll(eventManager.findAllEvents());
                tableViewEvents.refresh();
                handleCleanRequest(null);
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
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Eliminar Evento");
                alert.setHeaderText("¿Estas seguro de que quieres borrar este evento?");
                // Add confirmation and cancel buttons to the dialog
                ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
                ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(confirmButton, cancelButton);
                // Show the dialog and wait for user response
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == confirmButton) {
                    // Call the method to delete the event in the database
                    eventManager.deleteEvent(selectedEvent);
                    // Deleting the event from the TableView list
                    int selectedIndex = tableViewEvents.getSelectionModel().getSelectedIndex();
                    eventsData.remove(selectedIndex);
                    // Refresh the table with the modified data
                    tableViewEvents.refresh();
                    LOGGER.info("Event deleted by user confirmation.");
                } else {
                    LOGGER.info("Event deletion canceled by user.");
                }
                LOGGER.info("Event deleted");
            }
        } catch (Exception e) {
            LOGGER.severe("Error deleting the team: " + e.getMessage());
            lbError.setText("An error occured while deleting the event");
            lbError.setVisible(true);
        }
    }

    private void handleSearchRequest(ActionEvent event) {
        try {
            String selectedCriteria = cbBusqueda.getValue();

            Collection<Event> filteredEvents = null;
            switch (selectedCriteria) {
                case "Buscar todos los eventos":
                    filteredEvents = eventManager.findAllEvents();
                    break;
                case "Buscar eventos por Juego":
                    filteredEvents = eventManager.findEventsByGame(cbJuego.getValue());
                    break;
                case "Buscar eventos por ONG":
                    filteredEvents = eventManager.findEventsByONG(tfONG.getText());
                    break;
                default:
                    break;
            }

            if (filteredEvents != null) {
                // Limpiar la tabla y agregar los eventos filtrados
                eventsData.clear();
                eventsData.addAll(filteredEvents);
                tableViewEvents.refresh();
            }
        } catch (Exception ex) {
            LOGGER.severe("Error al buscar eventos: " + ex.getMessage());
        }
    }

}
