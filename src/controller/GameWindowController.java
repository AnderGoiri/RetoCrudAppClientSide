package controller;

import businessLogic.BusinessLogicException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Admin;
import model.Game;
import model.User;

public class GameWindowController extends GenericController {

    @FXML
    private HBox hBoxMenu;

    @FXML
    private TableView tbGames;
    /**
    * Game's id data table column.
    */
    @FXML
    private TableColumn tbcolId;
    /**
    * Game's name data table column.
    */
    @FXML
    private TableColumn tbcolName;
    /**
    * Game's genre data table column.
    */
    @FXML
    private TableColumn tbcolGenre;
    /**
    * Game's platform data table column.
    */
    @FXML
    private TableColumn tbcolPlatform;
    /**
    * Game's pvp type data table column.
    */
    @FXML
    private TableColumn tbcolPVPType;
    /**
    * Game's releasedate data table column.
    */
    @FXML
    private TableColumn tbcolReleaseDate;
    @FXML
    private Label label;

    @FXML
    private Button btnExit;
    
    @FXML
    private ComboBox cmbSearch;
    
    @FXML
    private TextField tfSearchData;
    
    @FXML
    private ComboBox cmbPVPType;
    
    @FXML
    private DatePicker dpReleaseDate;
    
    
    /**
     * User's table data model.
     */
    private ObservableList<Game> gamesData;

    /**
     * Method for initializing GestionUsuarios Stage. 
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.setScene(scene);
            stage.setTitle("Gestion de Juegos");
            stage.setResizable(false);
            
            //tbGames = new TableView<>();
            //tbGames.setEditable(true);
            //stage.setOnShowing(this::handleWindowShowing);
            //Add property change listeners for controls
            /*tfLogin.textProperty().addListener(this::handleTextChanged);
            tfNombre.textProperty().addListener(this::handleTextChanged);*/
            //tbGames.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);
            
            //These following lines are an example workaround to allow Menu Help 
            //to fire an action.It is preferable to avoid this but if you need it...
            
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
            //Set department combo data model.
            
            
            ObservableList<Game> games= 
                    FXCollections.observableArrayList(gameManager.getAllGames());
            
            
            //cbDepartamentos.setItems(departments);
            //Add focus event handler.
            //tfLogin.focusedProperty().addListener(this::focusChanged);
            //Set factories for cell values in users table columns.
            //tbcolId = new TableColumn("id");
            tbcolId.setCellValueFactory(
                    new PropertyValueFactory<>("id"));
            tbcolName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            tbcolName.setCellFactory(
                TextFieldTableCell.forTableColumn());           
            tbcolGenre.setCellValueFactory(
                    new PropertyValueFactory<>("genre"));
            tbcolGenre.setCellFactory(
                TextFieldTableCell.forTableColumn());
            tbcolPlatform.setCellValueFactory(
                    new PropertyValueFactory<>("platform"));
            tbcolPlatform.setCellFactory(
                TextFieldTableCell.forTableColumn());
            tbcolPVPType.setCellValueFactory(
                    new PropertyValueFactory<>("PVPType"));
            /*tbcolPVPType.setCellFactory(
                TextFieldTableCell.forTableColumn());*/
            tbcolReleaseDate.setCellValueFactory(
                    new PropertyValueFactory<>("releaseDate"));
            /*tbcolReleaseDate.setCellFactory(
                DatePicker.forTableColumn());*/
            
            //Create an obsrvable list for users table.
            ObservableList<Game> gamesData=FXCollections.observableArrayList(gameManager.getAllGames());
            //Set table model.
            
            tbGames.setEditable(true);
            
            tbGames.setItems(gamesData);
            /*tbGames.getColumns().addAll(tbcolId, tbcolName, tbcolGenre, 
                    tbcolPlatform, tbcolPVPType, tbcolReleaseDate);*/
            
                    
             // Create a context menu
            ContextMenu contextMenu = new ContextMenu();

            // CRUD options
            MenuItem createItem = new MenuItem("    Create Game");
            MenuItem readItem = new MenuItem("    Read Game");
            MenuItem updateItem = new MenuItem("    Update Game");
            MenuItem deleteItem = new MenuItem("    Delete Game");
            MenuItem eventsItem = new MenuItem("    Events");
            MenuItem teamsItem = new MenuItem("    Teams");
            MenuItem gamesItem = new MenuItem("    Games");
            
            // Separator
            SeparatorMenuItem separator = new SeparatorMenuItem();
          
            //TODO
            // Add actions to CRUD options (you can customize these actions)
            createItem.setOnAction(e -> System.out.println("Create action"));
            readItem.setOnAction(e -> System.out.println("Read action"));
            updateItem.setOnAction(e -> System.out.println("Update action"));
            deleteItem.setOnAction(e -> System.out.println("Delete action"));
            eventsItem.setOnAction(e -> System.out.println("Events action"));
            teamsItem.setOnAction(e -> System.out.println("Teams action"));
            gamesItem.setOnAction(e -> System.out.println("Games action"));
            
            
            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(createItem, readItem, updateItem, deleteItem, separator, eventsItem, teamsItem, gamesItem);

            // Attach the context menu to the root pane
            root.setOnContextMenuRequested(event -> 
                contextMenu.show(root, event.getScreenX(), event.getScreenY()));
            
            
            stage.setScene(scene);
            //Show window.
            stage.show();
        }catch(Exception e){
            showErrorAlert("No se ha podido abrir la ventana.\n"+
                            e.getMessage());
        }
    }

    @FXML
    private void handleImprimirButton() {
        // Example method for handling the "Imprimir" button click
        System.out.println("Imprimir button clicked!");
    }

    @FXML
    private void handleSalirButton() {
        // Example method for handling the "Salir" button click
        System.out.println("Salir button clicked!");
    }

    // You can add more methods to handle other events or perform specific actions

}
