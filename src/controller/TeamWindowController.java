package controller;

import exceptions.MaxCharException;
import java.util.Date;
import java.util.Observable;
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
    private ComboBox cmbBusqueda;

    @FXML
    private Button btnLimpiar;

    @FXML
    private ImageView imgLimpiar;

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

    private ObservableList<Team> teamsData;
    
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene (root, 1366, 768);
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
            
            //TODO Mostrar datos tabla
            
            /**TODO
            //Obtains the layout containing the menu bar from the scene node graph
            HBox hBoxMenu = (HBox)root.getChildrenUnmodifiable().get(0);
            //Get the menu bar from the children of the layout got before
            MenuBar menuBar = (MenuBar) hBoxMenu.getChildren().get(0);
            //Get the second menu from the menu bar
            Menu menuHelp = menuBar.getMenus().get(1);
            menuHelp.getItems().get(0).fire();
            **/
            
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
            cmbBusqueda.setOnAction(this::handleComboBoxSelection);
            
            
            btnBuscar.setDefaultButton(true);
            
            if(tbTeam.getItems().isEmpty()){
                Label noTeamPlaceholder = new Label("There are no teams currently.");
                tbTeam.setPlaceholder(noTeamPlaceholder);
            }
            
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
            lblError.setText("Ha ocurrido un error inesperado.");
            lblError.setVisible(true);
        }
    }
    
    public void handleComboBoxSelection (ActionEvent event){
        String selectedNamedQuery = (String) cmbBusqueda.getSelectionModel().getSelectedItem();
        switch (selectedNamedQuery) {
            case "Todos":
               btnBuscar.setDisable(false); 
            case "Por nombre":
                 btnBuscar.setDisable(false); 
            case "Por fecha":
                 btnBuscar.setDisable(false); 
            case "Por coach":
                 btnBuscar.setDisable(false); 
            case "Equipos con victorias":
                 btnBuscar.setDisable(false); 
            case "Mis equipos":
                 btnBuscar.setDisable(false); 
            case "":
                btnBuscar.setDisable(true);
        }
    }
    
    public void handleOnTextNotEmpty(Observable observable) {
    try {
        if (!tfNombre.getText().isEmpty() && !tfCoach.getText().isEmpty() && dpFundacion.getValue() != null){
            //TODO if (User is a Player)
            btnCrear.setDisable(false);
        } else {
            btnCrear.setDisable(true);
        }
    } catch (MaxCharException e) {
        
    }
    
    }
    


}


