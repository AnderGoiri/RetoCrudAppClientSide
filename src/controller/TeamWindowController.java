package controller;

import businessLogic.BusinessLogicException;
import exceptions.MaxCharException;
import exceptions.WrongCriteriaException;
import extra.DatePickerCellTeam;
import java.io.FileReader;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Team;

public class TeamWindowController extends GenericController {

    private Stage stage;
    private final static Logger LOGGER = Logger.getLogger(TeamWindowController.class.getName());
    
    @FXML
    private HBox hBoxMenu;

    @FXML
    private TableView tbTeam;

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
     
    public void initStage(Parent root) {
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

            tbcolFundacion.setCellValueFactory(new PropertyValueFactory<>("foundation"));
            
            //TODO Cambiar a relative path
            configFile.load(new FileReader("C:\\Users\\2dam\\Documents\\GitHub\\RetoCrudAppClientSide\\src\\config\\parameters.properties"));
            dateFormatPattern = configFile.getProperty("dateFormatPattern");
            
            tbcolFundacion.setCellFactory(DatePickerCellTeam.forTableColumn(dateFormatPattern));

            teamsData = FXCollections.observableArrayList(teamManager.findAllTeams());
            tbTeam.setItems(teamsData);
            
            /**
             * TODO //Obtains the layout containing the menu bar from the scene
             * node graph HBox hBoxMenu =
             * (HBox)root.getChildrenUnmodifiable().get(0); //Get the menu bar
             * from the children of the layout got before MenuBar menuBar =
             * (MenuBar) hBoxMenu.getChildren().get(0); //Get the second menu
             * from the menu bar Menu menuHelp = menuBar.getMenus().get(1);
             * menuHelp.getItems().get(0).fire();
            *
             */
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
            cmbBusqueda.requestFocus();
            cmbBusqueda.setOnAction((event) -> {
                try {
                    this.handleComboBoxSelection(event);
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(TeamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    showErrorAlert("No se ha podido abrir la ventana.");
                } catch (WrongCriteriaException ex) {
                    lblError.setText("Rellena el campo correcto para hacer una búsqueda con parámetro.");
                    lblError.setVisible(true);
                }
            });

            btnBuscar.setDefaultButton(true);

            //TODO Comprobar
            if(cmbBusqueda.getValue().equals("")){
                Label SelectPlaceholder = new Label("Selecciona un tipo de búsqueda para mostrar datos.");
                tbTeam.setPlaceholder(SelectPlaceholder);
            }
            //TODO Comprobar
            if(tbTeam.getItems().isEmpty()){
                Label noTeamPlaceholder = new Label("No existen datos que mostrar.");
                tbTeam.setPlaceholder(noTeamPlaceholder);
                //tbTeam.getPlaceholder().isVisible();
            }
            stage.show();
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha ocurrido un error inesperado.");
            lblError.setVisible(true);
        }
    }

    public void handleComboBoxSelection(ActionEvent event) throws BusinessLogicException, WrongCriteriaException {
        String selectedNamedQuery = (String) cmbBusqueda.getSelectionModel().getSelectedItem();
        if (selectedNamedQuery != null) {
            switch (selectedNamedQuery) {
                case "Todos":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                        teamsData = FXCollections.observableArrayList(teamManager.findAllTeams());
                        tbTeam.setItems(teamsData);
                    }
                    break;
                case "Por nombre":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                        if (!tfNombre.equals("")){
                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsByName());
                            tbTeam.setItems(teamsData);
                        } else {
                            throw new WrongCriteriaException();
                        } 
                    }
                    break;
                case "Por fecha":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                       if (!dpFundacion.equals("")){
                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsByDate());
                            tbTeam.setItems(teamsData);
                        } else {
                            throw new WrongCriteriaException();
                        } 
                    }
                    break;
                case "Por coach":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                        if (!tfCoach.equals("")){
                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsByCoach());
                            tbTeam.setItems(teamsData);
                        } else {
                            throw new WrongCriteriaException();
                        } 
                    }
                    break;
                case "Equipos con victorias":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){

                            teamsData = FXCollections.observableArrayList(teamManager.findTeamsWithWins());
                            tbTeam.setItems(teamsData);

                    }
                    break;
                case "Mis equipos":
                    btnBuscar.setDisable(false);
                    if (btnBuscar.isPressed()){
                            teamsData = FXCollections.observableArrayList(teamManager.findMyTeams());
                            tbTeam.setItems(teamsData);
     
                    }
                    break;
                case "":
                    btnBuscar.setDisable(true);
                    break;
            }
        }
    }

    /*public void handleOnTextNotEmpty(Observable observable) {
        try {
            if (!tfNombre.getText().isEmpty() && !tfCoach.getText().isEmpty() && dpFundacion.getValue() != null) {
                //TODO if (User is a Player)
                btnCrear.setDisable(false);
            } else {
                btnCrear.setDisable(true);
            }
        } catch (MaxCharException e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha sobrepasado el límite de carácteres disponibles.");
            lblError.setVisible(true);
        }

    }*/



}