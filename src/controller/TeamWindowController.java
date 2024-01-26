package controller;

import businessLogic.BusinessLogicException;
import exceptions.MaxCharException;
import exceptions.TextFormatException;
import exceptions.WrongCriteriaException;
import extra.DatePickerCellTeam;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Observable;
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
import javafx.scene.control.Button;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Player;
import model.Team;
import model.User;

public class TeamWindowController extends GenericController {

    private Stage stage;
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
    
    private String dateFormatPattern;
    
    ObservableList<Team> teamsData;
     
    public void initStage(Parent root, User user) {
        try {
            Scene scene = new Scene (root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Equipos");
            stage.setResizable(false);
            
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
 
            tbcolNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcolEntrenador.setCellValueFactory(new PropertyValueFactory<>("coach"));
            tbcolFundacion.setCellValueFactory(new PropertyValueFactory<>("foundation"));
            
            //TODO Cambiar a relative path
            //configFile.load(new FileReader("C:\\Users\\2dam\\Documents\\GitHub\\RetoCrudAppClientSide\\src\\config\\parameters.properties"));
            //dateFormatPattern = configFile.getProperty("dateFormatPattern");
            //tbcolFundacion.setCellFactory(DatePickerCellTeam.forTableColumn(dateFormatPattern));
            
            teamsData = FXCollections.observableArrayList(teamManager.findAllTeams());
            tbTeam.setItems(teamsData);

            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    "Todos",
                    "Por nombre",
                    "Por fecha",
                    "Por coach",
                    "Equipos con victorias",
                    "Mis Equipos",
                    ""
            );
            cmbBusqueda.setItems(namedQueriesList);
            cmbBusqueda.setValue("");
            Label SelectPlaceholder = new Label("Selecciona un tipo de búsqueda para mostrar datos.");
            tbTeam.setPlaceholder(SelectPlaceholder);

            stage.show();
            
            if(tbTeam.getItems().isEmpty()){
                Label noTeamPlaceholder = new Label("No existen datos que mostrar.");
                tbTeam.setPlaceholder(noTeamPlaceholder); 
            }
            
            cmbBusqueda.requestFocus();

            btnBuscar.setDefaultButton(true);
            
            //Obtains the layout containing the menu bar from the scene node graph
            HBox hBoxMenu= (HBox)root.getChildrenUnmodifiable().get(0);
            //Get the menu bar from the children of the layout got before
            MenuBar menuBar= (MenuBar)hBoxMenu.getChildren().get(0);
            //Get the second menu from the menu bar
            Menu menuHelp=menuBar.getMenus().get(1);
            //Add a listener for the showing property that fires the action event
            //on the first menu item of that menu
            menuHelp.showingProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if (newValue) {
                        menuHelp.getItems().get(0).fire();
                    }
                }
            );

            cmbBusqueda.setOnAction((event) -> {
                try {
                    this.handleComboBoxSelection(event); 
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
            
            // CRUD options
            MenuItem cleanFormItem = new MenuItem("Limpiar formulario.");
            MenuItem createTeamItem = new MenuItem("Crear equipo.");
            MenuItem modifyTeamItem = new MenuItem("Modificar equipo.");
            
            // Add actions to CRUD options of context menu
            cleanFormItem.setOnAction(event -> handleCleanRequest(event));
            createTeamItem.setOnAction(event -> handleCreateTeam(event));
            modifyTeamItem.setOnAction(event -> handleOnModifyTeam(event));

            // Add CRUD options to the context menu
            root.setOnContextMenuRequested(event -> contextMenu.show(root, event.getScreenX(), event.getScreenY()));

            // Set handler for cleaning button
            btnLimpiar.setOnAction(event -> handleCleanRequest(event));

            // Set listeners for handlers of empty text
            tfNombre.textProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(user));
            tfCoach.textProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(user));
            dpFundacion.valueProperty().addListener((observable, oldValue, newValue) -> handleTextNotEmpty(user));
            
            // Selecting info from the table into the form
            // TODO Check multiple selection in table
            tbTeam.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue!=null){
                    tfNombre.setText(newValue.getName());
                    tfCoach.setText(newValue.getCoach());
                    try {
                        dpFundacion.setValue(newValue.getFoundation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    } catch (IOException ex) {
                        Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            // Creating a team
            btnCrear.setOnAction(event -> handleCreateTeam(event));
            btnCrear.setOnAction(event -> handleCleanRequest(event));
            
            
            // Modifying a team
            
            
            stage.setOnCloseRequest(event -> super.handleCloseRequest(event));
            //TODO Change it to go back to login
            btnSalir.setOnAction(event -> super.handleBtnClose(event));
            
            
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha ocurrido un error inesperado.");
            lblError.setVisible(true);
        }
    }

    public void handleComboBoxSelection(ActionEvent event) throws BusinessLogicException, WrongCriteriaException {
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
                                teamsData = FXCollections.observableArrayList(teamManager.findAllTeams());
                                tbTeam.setItems(teamsData);
                            } catch (BusinessLogicException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                showErrorAlert("No se ha podido abrir la ventana.");
                            }
                        }
                    });
                    break;
                case "Por nombre":
                    btnBuscar.setDisable(false);
                    btnBuscar.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                teamsData.clear();
                                teamsData = FXCollections.observableArrayList(teamManager.findTeamsByName(tfNombre.getText()));
                                tbTeam.setItems(teamsData);
                            } catch (BusinessLogicException ex) {
                                Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                lblError.setText("No se ha rellenado el campo correspondiente.");
                                lblError.setVisible(true);
                                teamsData.clear();
                                Label noTeamPlaceholder = new Label("No existen datos correspondientes a la búsqueda.");
                                tbTeam.setPlaceholder(noTeamPlaceholder);
                            }
                        }
                    });
                    break;
                case "Por fecha":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()) {
                        if (!dpFundacion.getValue().toString().equals("")) {
                            teamsData.clear();
                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsByDate(dpFundacion.getValue().toString()));
                            tbTeam.setItems(teamsData);
                        } else {
                            throw new WrongCriteriaException();
                        }
                    }
                    break;
                case "Por coach":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                        if (!tfCoach.getText().equals("")){
                            teamsData.clear();
                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsByCoach(tfCoach.getText()));
                            tbTeam.setItems(teamsData);
                        } else {
                            throw new WrongCriteriaException();
                        } 
                    }
                    break;
                case "Equipos con victorias":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()) {
                        teamsData.clear();
                        teamsData = FXCollections.observableArrayList(teamManager.findTeamsWithWins());
                        tbTeam.setItems(teamsData);
                    }
                    break;
                case "Mis Equipos":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()) {
                        teamsData.clear();
                        //TODO Parametro player
                        teamsData = FXCollections.observableArrayList(teamManager.findMyTeams());
                        tbTeam.setItems(teamsData);
                    }
                    break;
                case "":
                    teamsData.clear();
                    btnBuscar.setDisable(true);
                    break;
            }
        }
    }
    
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
    
    public void handleTextNotEmpty(User user) {
        try {
            if (!tfNombre.getText().isEmpty() && !tfCoach.getText().isEmpty() && dpFundacion.getValue() != null) {
                if (user instanceof Player) {
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

    private void handleCreateTeam(ActionEvent event) {
        try {
            // Clearing the table and creating a Team
            teamsData.clear();
            Team newTeam = new Team();
            
            // Set the pattern to validate the tfNombre and tfCoach
            String regexName = "^[a-zA-Z0-9 ]+$"; 
            
            // Compare the pattern
            if (tfNombre.getText().matches(regexName)){
                newTeam.setName(tfNombre.getText());
            } else {
                LOGGER.warning("Format validation incorrect.");
                throw new TextFormatException();
            }
            // Compare the pattern
            if (tfCoach.getText().matches(regexName)){
                newTeam.setCoach(tfCoach.getText());
            } else {
                LOGGER.warning("Format validation incorrect.");
                throw new TextFormatException();
            }
            
            // Converting the LocalDate to Date
            LocalDate localDate = dpFundacion.getValue();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            newTeam.setFoundation(date);
            
            // Creating the previously set Team in the server side
            teamManager.createTeam(newTeam);
            lblError.setVisible(false);
        } catch (TextFormatException e) { 
            LOGGER.warning(e.getMessage());
            lblError.setText("El campo Nombre debe tener solamente carácteres alfanuméricos.");
            lblError.setVisible(true);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            
        }
    }

    private void handleOnModifyTeam(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void handleEmptyTable(ActionEvent event) {
        try {
            if (tbTeam.getItems().isEmpty()) {
                Label SelectPlaceholder = new Label("No hay datos que correspondan al criterio seleccionado.");
                tbTeam.setPlaceholder(SelectPlaceholder);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
