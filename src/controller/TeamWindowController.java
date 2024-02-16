package controller;

import exceptions.BusinessLogicException;
import exceptions.CoachFormatException;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.MaxCharException;
import exceptions.NameFormatException;
import exceptions.NoDataException;
import exceptions.ReadException;
import exceptions.TeamAlreadyExistsException;
import exceptions.TextFormatException;
import exceptions.UpdateException;
import exceptions.WrongCriteriaException;
import extra.DatePickerCellTeam;
import factory.TeamFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Observable;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;
import model.Team;
import model.User;

/**
 * FXML Controller class
 *
 * @author Jagoba Bartolomé Barroso
 */
public class TeamWindowController extends GenericController {

    //private Stage stage;
    private final static Logger LOGGER = Logger.getLogger(TeamWindowController.class.getName());

    @FXML
    private Label titulo;

    @FXML
    private HBox hBoxMenu;

    @FXML
    private TableView<Team> tbTeam;

    @FXML
    private TableColumn<Team, String> tbcolNombre;

    @FXML
    private TableColumn<Team, Date> tbcolFundacion;

    @FXML
    private TableColumn<Team, String> tbcolEntrenador;

    @FXML
    private Label lblNombre;

    @FXML
    private TextField tfNombre;

    @FXML
    private Label lblFundacion;

    @FXML
    private Label lblCoach;

    @FXML
    private TextField tfCoach;

    @FXML
    private DatePicker dpFundacion;

    @FXML
    private Button btnBuscar;

    @FXML
    private Label lblBusqueda;

    @FXML
    private ComboBox<String> cmbBusqueda;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnUnirse;

    @FXML
    private Label lblError;

    @FXML
    private Button btnEliminar;

    private Properties configFile = new Properties();

    private InputStream input;

    private String dateFormatPattern;

    ObservableList<Team> teamsData;

    /**
     * Initializes the team window stage with necessary components, listeners,
     * and data. Disables/enables buttons and text fields based on the user's
     * role.
     *
     * @param root The root node of the scene.
     */
    public void initStage(Parent root) {
        try {
            // Window setters
            Scene uwu = new Scene(root);
            //stage = new Stage();

            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(uwu);
            stage.setTitle("Equipos");
            //stage.setResizable(false);

            // Disabling Buttons and Textfields
            btnUnirse.setDisable(true);
            btnBuscar.setDisable(true);
            btnCrear.setDisable(true);
            btnModificar.setDisable(true);
            btnEliminar.setDisable(true);

            tfNombre.setDisable(false);
            tfCoach.setDisable(false);
            dpFundacion.setDisable(false);

            cmbBusqueda.setDisable(false);

            btnSalir.setDisable(false);
            btnLimpiar.setDisable(false);

            lblError.setVisible(false);

            tbTeam.setEditable(false);

            // Getters for the values of each cell
            tbcolNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolEntrenador.setCellValueFactory(new PropertyValueFactory<>("coach"));
            tbcolFundacion.setCellValueFactory(new PropertyValueFactory<>("foundation"));

            // Getting date pattern from properties file
            input = getClass().getClassLoader().getResourceAsStream("config/parameters.properties");
            configFile.load(input);
            dateFormatPattern = configFile.getProperty("dateFormatPattern");
            tbcolFundacion.setCellFactory(DatePickerCellTeam.forTableColumn(dateFormatPattern));
            // Setting all info from the server into the TableView
            teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findAllTeams());
            tbTeam.setItems(teamsData);

            // All combo options
            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    "Todos",
                    "Por nombre",
                    //"Por fecha",
                    "Por coach"/*,
                    "Equipos con victorias",
                    "Mis Equipos"*/,
                    ""
            );
            // Setting default selected item for search combo
            cmbBusqueda.setItems(namedQueriesList);
            cmbBusqueda.setValue("");

            // Setting default message for placeholder
            Label SelectPlaceholder = new Label("Selecciona un tipo de búsqueda para mostrar datos.");
            tbTeam.setPlaceholder(SelectPlaceholder);

            // Show the view
            stage.show();

            // Check if there is no data in Team table
            if (tbTeam.getItems().isEmpty()) {
                Label noTeamPlaceholder = new Label("No existen datos que mostrar.");
                tbTeam.setPlaceholder(noTeamPlaceholder);
            }

            // Put focus on Search combo
            cmbBusqueda.requestFocus();

            // Set search button as default
            btnBuscar.setDefaultButton(true);

            //Adding Tooltip to btnBuscar
            btnBuscar.setTooltip(new Tooltip("Buscar equipos."));

            //Obtains the layout containing the menu bar from the scene node graph
            /*HBox hBoxMenu = (HBox) root.getChildrenUnmodifiable().get(0);
            //Get the menu bar from the children of the layout got before
            MenuBar menuBar = (MenuBar) hBoxMenu.getChildren().get(0);
            //Get the second menu from the menu bar
            Menu menuHelp = menuBar.getMenus().get(1);
            //Add a listener for the showing property that fires the action event
            //on the first menu item of that menu
            menuHelp.showingProperty().addListener(
                    (observableValue, oldValue, newValue) -> {
                        if (newValue) {
                            menuHelp.getItems().get(0).fire();
                        }
                    }
            );*/
            // Added a handler for the Search combo
            cmbBusqueda.setOnAction((event) -> {
                try {
                    this.handleComboBoxSelection(event, getUser());
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    showErrorAlert("No se ha podido abrir la ventana.");
                } catch (WrongCriteriaException ex) {
                    lblError.setText("Rellena el campo correcto para hacer una búsqueda con parámetro.");
                    lblError.setVisible(true);
                    teamsData.clear();
                }
            });

            // Create a context menu
            ContextMenu contextMenu = new ContextMenu();

            // CRUD menu options
            MenuItem cleanFormItem = new MenuItem("Limpiar formulario.");
            MenuItem createTeamItem = new MenuItem("Crear equipo.");
            MenuItem modifyTeamItem = new MenuItem("Modificar equipo.");

            // Add actions to CRUD options of context menu
            cleanFormItem.setOnAction(event -> handleCleanRequest(event));
            createTeamItem.setOnAction(event -> handleCreateTeam(event));
            modifyTeamItem.setOnAction(event -> handleModifyTeam(event));

            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(cleanFormItem, createTeamItem, modifyTeamItem);
            root.setOnContextMenuRequested(event -> contextMenu.show(root, event.getScreenX(), event.getScreenY()));

            // Set handler for cleaning button
            btnLimpiar.setOnAction(event -> handleCleanRequest(event));

            //Adding tooltip to btnLimpiar
            btnLimpiar.setTooltip(new Tooltip("Limpiar campos."));

            // Set listeners for handlers of empty text
            tfNombre.textProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(getUser()));
            tfCoach.textProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(getUser()));
            dpFundacion.valueProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(getUser()));

            // Selecting info from the table into the form
            tbTeam.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.intValue() >= 0 && getUser().getUser_type().equalsIgnoreCase("player")) {
                    LOGGER.info("Selected a table row.");
                    btnModificar.setDisable(false);
                    btnUnirse.setDisable(false);
                    btnEliminar.setDisable(false);

                    Team selectedTeam = tbTeam.getSelectionModel().getSelectedItem();
                    tfNombre.setText(selectedTeam.getName());
                    tfCoach.setText(selectedTeam.getCoach());
                    tbTeam.getSelectionModel().clearSelection();
                    try {
                        dpFundacion.setValue(selectedTeam.getFoundation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    } catch (IOException | ParseException ex) {
                        Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //Cleaning the form if there is no selected row
                    tfNombre.clear();
                    tfCoach.clear();
                    dpFundacion.getEditor().clear();

                    btnModificar.setDisable(true);
                    btnUnirse.setDisable(true);
                    btnEliminar.setDisable(true);
                }
                handleTextNotEmpty(getUser());
            });
            
            //Cleaning the form if there is no selected row
            if (tbTeam.getSelectionModel().getSelectedIndex() == -1) {
                tfNombre.clear();
                tfCoach.clear();
                dpFundacion.getEditor().clear();
            }

            // Creating a team
            btnCrear.setOnAction(event -> handleCreateTeam(event));

            // Modifying a team
            btnModificar.setOnAction(event -> handleModifyTeam(event));

            // Deleting a team
            btnEliminar.setOnAction(event -> handleDeleteTeam(event));

            // Joining a team
            btnUnirse.setOnAction(event -> handleJoinTeam(event, getUser()));

            // Check if any of the buttons is pressed
            boolean buttonPressed = btnCrear.isPressed() || btnModificar.isPressed() || btnEliminar.isPressed() || btnUnirse.isPressed();

            // Check if all text fields are empty
            boolean allFieldsEmpty = tfNombre.getText().isEmpty() && tfCoach.getText().isEmpty() && dpFundacion.getValue() == null;

            // If any button is pressed and all fields are empty, throw NoDataException
            if (buttonPressed && allFieldsEmpty) {
                throw new NoDataException("No hay datos con los que operar.");
            }

            // Closing the window
            stage.setOnCloseRequest(event -> super.handleCloseRequest(event));
            //TODO Change it to go back to login
            btnSalir.setOnAction(event -> super.handleBtnClose(event));

        } catch (NoDataException e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("No hay datos.");
            lblError.setVisible(true);
        } catch (ReadException e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha ocurrido un error al buscar equipos.");
            lblError.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha ocurrido un error inesperado.");
            lblError.setVisible(true);
        }
    }

    /**
     * Handles the selection of a named query from the search combo box and
     * performs the corresponding action. Disables/enables buttons and updates
     * the TableView based on the selected named query.
     *
     * @param event The ActionEvent triggering the method call.
     * @param user The User for whom the named query is performed.
     * @throws BusinessLogicException If a business logic error occurs during
     * the process.
     * @throws WrongCriteriaException If the criteria for a named query are not
     * met.
     */
    public void handleComboBoxSelection(ActionEvent event, User user) throws BusinessLogicException, WrongCriteriaException {
        String selectedNamedQuery = (String) cmbBusqueda.getValue();
        if (selectedNamedQuery != null) {
            lblError.setVisible(false);
            switch (selectedNamedQuery) {
                case "Todos":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                teamsData.clear();
                                teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findAllTeams());
                                tbTeam.setItems(teamsData);
                            } catch (ReadException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                showErrorAlert("No se han podido cargar los datos.");
                            }
                        }
                    });
                    handleEmptyTable();
                    break;
                case "Por nombre":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                teamsData.clear();
                                teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findTeamsByName(tfNombre.getText()));
                                tbTeam.setItems(teamsData);
                            } catch (ReadException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("No se han podido cargar los datos.");
                                lblError.setVisible(true);
                                teamsData.clear();
                                Label noTeamPlaceholder = new Label("No existen datos correspondientes a la búsqueda.");
                                tbTeam.setPlaceholder(noTeamPlaceholder);
                            }
                        }
                    });
                    handleEmptyTable();
                    break;
                /*case "Por fecha":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                LocalDate selectedDate = dpFundacion.getValue();
                                if (selectedDate != null) {
                                    teamsData.clear();
                                    teamsData.addAll(TeamFactory.getTeamManager().findTeamsByDate(selectedDate.toString()));
                                    tbTeam.setItems(teamsData);

                                    if (teamsData.isEmpty()) {
                                        throw new BusinessLogicException("No existen equipos con la fecha seleccionada.");
                                    }
                                } else {
                                    throw new WrongCriteriaException();
                                }
                            } catch (BusinessLogicException | WrongCriteriaException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("No se han encontrado equipos con la fecha seleccionada.");
                                lblError.setVisible(true);
                                teamsData.clear();
                                Label noTeamPlaceholder = new Label("No existen datos correspondientes a la búsqueda.");
                                tbTeam.setPlaceholder(noTeamPlaceholder);
                            } catch (Exception ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("Ha ocurrido un error al buscar equipos por fecha.");
                                lblError.setVisible(true);
                            }
                        }
                    });
                    handleEmptyTable();
                    break;
                 */
                case "Por coach":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                teamsData.clear();
                                teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findTeamsByCoach(tfCoach.getText()));
                                tbTeam.setItems(teamsData);
                            } catch (ReadException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("No se ha rellenado el campo correspondiente o no existe.");
                                lblError.setVisible(true);
                                teamsData.clear();
                                Label noTeamPlaceholder = new Label("No existen datos correspondientes a la búsqueda.");
                                tbTeam.setPlaceholder(noTeamPlaceholder);
                            }
                        }
                    });
                    handleEmptyTable();
                    break;
                /*case "Equipos con victorias":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()) {
                        teamsData.clear();
                        teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findTeamsWithWins());
                        tbTeam.setItems(teamsData);
                    }
                    handleEmptyTable();
                    break;
                case "Mis Equipos":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                teamsData.clear();
                                teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findMyTeams((Player) user));
                                tbTeam.setItems(teamsData);
                            } catch (BusinessLogicException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("No se ha rellenado el campo correspondiente o no es correcto.");
                                lblError.setVisible(true);
                                teamsData.clear();
                                Label noTeamPlaceholder = new Label("No existen datos correspondientes a la búsqueda.");
                                tbTeam.setPlaceholder(noTeamPlaceholder);
                            }
                        }
                    });
                    handleEmptyTable();
                    break;*/
                case "":
                    teamsData.clear();
                    btnBuscar.setDisable(true);
                    break;

            }
        }
    }

    /**
     * Handles the action of clearing input fields and resetting values in the
     * form.
     *
     * @param event The ActionEvent triggering the method call.
     */
    public void handleCleanRequest(ActionEvent event) {
        try {
            tfNombre.clear();
            tfCoach.clear();
            dpFundacion.getEditor().clear();
            cmbBusqueda.setValue("");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error cleaning the form", ex);
            lblError.setText("Error cleaning the form.");
            lblError.setVisible(true);
        }
    }

    /**
     * Handles the logic for enabling/disabling a button based on text input and
     * user type.
     *
     * @param user The User object for whom the validation is performed.
     * @throws MaxCharException If the length of the Name or Coach field exceeds
     * the allowed limit.
     */
    public void handleTextNotEmpty(User user) {
        try {
            if (!tfNombre.getText().isEmpty() && !tfCoach.getText().isEmpty() && dpFundacion.getValue() != null) {
                if (user.getUser_type().equalsIgnoreCase("player")) {
                    if (tfNombre.getText().length() > 60 || tfCoach.getText().length() > 60) {
                        throw new MaxCharException();
                    } else {
                        btnCrear.setDisable(false);
                    }
                }
            } else {
                btnCrear.setDisable(true);
            }
        } catch (MaxCharException e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha sobrepasado el límite de carácteres disponibles.");
            lblError.setVisible(true);
        }

    }

    /**
     * Handles the action of creating a new team based on user input. Clears the
     * existing table, validates input fields, creates a new team, sends the
     * team information to the server, and updates the TableView.
     *
     * @param event The ActionEvent triggering the method call.
     * @throws TextFormatException If the Name or Coach field does not match the
     * required format.
     * @throws Exception If an error occurs during the process of creating the
     * team.
     */
    private void handleCreateTeam(ActionEvent event) {
        try {
            // Clearing the table and creating a Team
            teamsData.clear();
            Team newTeam = new Team();

            // Set the pattern to validate the tfNombre and tfCoach
            String regexName = "^[a-zA-Z0-9 ]+$";

            // Compare the pattern
            if (tfNombre.getText().matches(regexName)) {
                newTeam.setName(tfNombre.getText());
            } else {
                LOGGER.warning("Format validation incorrect." + tfNombre.getText());
                throw new TextFormatException("El campo Nombre debe tener caracteres alfanuméricos y espacios.");
            }
            // Compare the pattern
            if (tfCoach.getText().matches(regexName)) {
                newTeam.setCoach(tfCoach.getText());
            } else {
                LOGGER.warning("Format validation incorrect.");
                throw new TextFormatException("El campo Coach debe tener caracteres alfanuméricos y espacios.");
            }

            // Converting the LocalDate to Date
            LocalDate localDate = dpFundacion.getValue();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            newTeam.setFoundation(date);

            // Creating the previously set Team in the server side
            TeamFactory.getTeamManager().createTeam(newTeam);
            teamsData.clear();
            teamsData = FXCollections.observableArrayList(TeamFactory.getTeamManager().findAllTeams());
            tbTeam.setItems(teamsData);
            tbTeam.refresh();

            //Cleaning the form if there is no selected row
            tfNombre.clear();
            tfCoach.clear();
            dpFundacion.getEditor().clear();

            // Changing label
            Label SelectPlaceholder = new Label("Selecciona un tipo de búsqueda para mostrar datos.");
            tbTeam.setPlaceholder(SelectPlaceholder);

            lblError.setVisible(false);
        } catch (TextFormatException e) {
            LOGGER.warning(e.getMessage());
            lblError.setText("Formato de texto incorrecto.");
            lblError.setVisible(true);
        } catch (CreateException e) {
            LOGGER.severe(e.getMessage());
            // Handle other exceptions if needed
            lblError.setText("No se ha podido crear el equipo.");
            lblError.setVisible(true);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            // Handle other exceptions if needed
            lblError.setText("Ha ocurrido un error al crear el equipo.");
            lblError.setVisible(true);
        }
    }

    /**
     * Handles the scenario when the TableView is empty by setting a placeholder
     * label. If the TableView contains no items, it sets a label indicating
     * that there is no data matching the selected criteria as a placeholder.
     */
    private void handleEmptyTable() {
        try {
            // Check if the TableView is empty
            if (tbTeam.getItems().isEmpty()) {
                // Set a placeholder label for an empty table
                Label selectPlaceholder = new Label("No data matching the selected criteria.");
                tbTeam.setPlaceholder(selectPlaceholder);
            }

        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Handles the action of modifying a team based on user input.
     *
     * @param event The ActionEvent triggering the method call.
     * @throws TeamAlreadyExistsException If the modified team information
     * matches an existing team.
     * @throws CoachFormatException If the Coach field does not match the
     * required format.
     * @throws NameFormatException If the Name field does not match the required
     * format.
     * @throws Exception If an error occurs during the process of modifying the
     * team.
     */
    private void handleModifyTeam(ActionEvent event) {
        try {
            lblError.setVisible(false);
            // Get the selected team in the table
            // Get the selected team in the table
            Team selectedTeam = tbTeam.getSelectionModel().getSelectedItem();

            // Check if there is a selected team
            if (selectedTeam != null) {
                // Set the pattern to validate the tfNombre and tfCoach
                String regexName = "^[a-zA-Z0-9 ]+$";

                // Compare the pattern
                if (!tfNombre.getText().matches(regexName)) {
                    throw new NameFormatException("El campo Nombre debe tener caracteres alfanuméricos y espacios.");
                }

                // Compare the pattern
                if (!tfCoach.getText().matches(regexName)) {
                    throw new CoachFormatException("El campo Coach debe tener caracteres alfanuméricos y espacios.");
                }

                // Convert LocalDate to Date
                LocalDate localFundacion = dpFundacion.getValue();
                Date newDate = Date.from(localFundacion.atStartOfDay(ZoneId.systemDefault()).toInstant());

                // Check if the form info did change from the selected item in the TableView
                if (!tfNombre.getText().equals(selectedTeam.getName())
                        || !tfCoach.getText().equals(selectedTeam.getCoach())
                        || !newDate.equals(selectedTeam.getFoundation())) {

                    // Call the method to modify the team in the database
                    selectedTeam.setName(tfNombre.getText());
                    selectedTeam.setCoach(tfCoach.getText());
                    selectedTeam.setFoundation(newDate);

                    TeamFactory.getTeamManager().modifyTeam(selectedTeam);

                    //Cleaning the form if there is no selected row
                    tfNombre.clear();
                    tfCoach.clear();
                    dpFundacion.getEditor().clear();

                } else {
                    throw new TeamAlreadyExistsException("El equipo no ha sido modificado.");
                }

                // Refresh the table with the modified data
                tbTeam.refresh();

                LOGGER.info("Team modified.");
            } else {
                LOGGER.warning("No team selected.");
                lblError.setText("No team selected");
                lblError.setVisible(true);
            }

        } catch (TeamAlreadyExistsException e) {
            LOGGER.severe("Error modifying the team: " + e.getMessage());
            lblError.setText(e.getMessage());
            lblError.setVisible(true);
        } catch (CoachFormatException e) {
            LOGGER.severe("Error modifying the team: " + e.getMessage());
            lblError.setText(e.getMessage());
            lblError.setVisible(true);
        } catch (NameFormatException e) {
            LOGGER.severe("Error modifying the team: " + e.getMessage());
            lblError.setText(e.getMessage());
            lblError.setVisible(true);
        } catch (UpdateException e) {
            LOGGER.severe("Error modifying the team: " + e.getMessage());
            lblError.setText("An error occurred while modifying the team.");
            lblError.setVisible(true);
        } catch (Exception e) {
            LOGGER.severe("Error modifying the team: " + e.getMessage());
            lblError.setText("An unexpected error occurred.");
            lblError.setVisible(true);
        }
    }

    /**
     * Handles the action of deleting a team.
     *
     * @param event The ActionEvent triggering the method call.
     * @throws Exception If an error occurs during the process of deleting the
     * team.
     */
    private void handleDeleteTeam(ActionEvent event) {
        try {
            lblError.setVisible(false);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar equipo.");
            alert.setContentText("Estás seguro de que deseas borrar ese equipo?");

            ButtonType confirmButton = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == confirmButton) {
                // Get the selected team in the table
                Team selectedTeam = tbTeam.getSelectionModel().getSelectedItem();

                // Check if there is a selected team
                if (selectedTeam != null) {
                    // Call the method to delete the team in the database
                    TeamFactory.getTeamManager().deleteTeam(selectedTeam);

                    // Deleting the team from the TableView list
                    int selectedIndex = tbTeam.getSelectionModel().getSelectedIndex();
                    teamsData.remove(selectedIndex);

                    // Refresh the table with the modified data
                    tbTeam.refresh();

                    LOGGER.info("Team deleted.");
                } else {
                    LOGGER.warning("No team selected.");
                    lblError.setText("No team selected");
                    lblError.setVisible(true);
                }
            }

        } catch (DeleteException e) {
            LOGGER.severe("Error deleting the team: " + e.getMessage());
            lblError.setText("An error occurred while deleting the team.");
            lblError.setVisible(true);
        } catch (Exception e) {
            LOGGER.severe("Error deleting the team: " + e.getMessage());
            lblError.setText("An unexpected error occurred.");
            lblError.setVisible(true);
        }
    }

    /**
     * Handles the action of joining a team by a user.
     *
     * @param event The ActionEvent triggering the method call.
     * @param user The User attempting to join the team.
     * @throws Exception If an error occurs during the process of joining the
     * team.
     */
    private void handleJoinTeam(ActionEvent event, User user) {
        try {
            lblError.setVisible(false);
            // Get the selected team in the table
            Team selectedTeam = tbTeam.getSelectionModel().getSelectedItem();

            // Check if there is a selected team
            if (selectedTeam != null) {
                // Call the method to join the team in the database
                //TeamFactory.getTeamManager().joinTeam(selectedTeam, (Player) user);

                // Refresh the table with the modified data
                tbTeam.refresh();

                tfNombre.clear();
                tfCoach.clear();
                dpFundacion.getEditor().clear();

                LOGGER.info("Team joined.");
                lblError.setText("Te has unido al equipo.");
            } else {
                LOGGER.warning("No team selected.");
                lblError.setText("No team selected");
                lblError.setVisible(true);
            }
        } catch (Exception e) {
            LOGGER.severe("Error joining the team: " + e.getMessage());
            lblError.setText("An unexpected error occurred.");
            lblError.setVisible(true);
        }
    }
}
