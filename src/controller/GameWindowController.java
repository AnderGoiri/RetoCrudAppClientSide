package controller;

import businessLogic.BusinessLogicException;
import extra.DatePickerCellGame;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ejb.CreateException;
import model.Admin;
import model.Game;
import model.PVPType;
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
    private TableColumn<Game, Long> tbcolId;
    /**
    * Game's name data table column.
    */
    @FXML
    private TableColumn<Game, String> tbcolName;
    /**
    * Game's genre data table column.
    */
    @FXML
    private TableColumn<Game, String> tbcolGenre;
    /**
    * Game's platform data table column.
    */
    @FXML
    private TableColumn<Game, String> tbcolPlatform;
    /**
    * Game's pvp type data table column.
    */
    @FXML
    private TableColumn<Game, PVPType> tbcolPVPType;
    /**
    * Game's releasedate data table column.
    */
    @FXML
    private TableColumn<Game, Date> tbcolReleaseDate;
    
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
    
    @FXML
    private Button btnAddRow;
    
    
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
            
            
            /*ObservableList<Game> games= 
                    FXCollections.observableArrayList(gameManager.getAllGames());*/
            
            
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
            tbcolPVPType.setCellFactory(ComboBoxTableCell.forTableColumn(PVPType.values()));
            tbcolPVPType.setOnEditCommit(
                (TableColumn.CellEditEvent<Game, PVPType> t) -> {
                try {
                            
                    ((Game)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPVPType(t.getNewValue());
                            Object selectedGame = tbGames.getSelectionModel().getSelectedItem();
                            //String gameID = String.valueOf(((Game)tbGames.getSelectionModel().getSelectedItem()).getId());
                            gameManager.updateGame(selectedGame);
                } catch (BusinessLogicException ex) {
                    Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }

                });
            tbcolReleaseDate.setCellValueFactory(
                    new PropertyValueFactory<>("releaseDate"));
            tbcolReleaseDate.setCellFactory(column -> new DatePickerCellGame());
            
            //Create an obsrvable list for users table.
            gamesData=FXCollections.observableArrayList(gameManager.getAllGames());
            //Set table model.            
            tbGames.setEditable(true);
            
            tbGames.setItems(gamesData);

                    
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
            deleteItem.setOnAction(e -> deleteSelectedItem());
            eventsItem.setOnAction(e -> System.out.println("Events action"));
            teamsItem.setOnAction(e -> System.out.println("Teams action"));
            gamesItem.setOnAction(e -> System.out.println("Games action"));
            
            
            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(createItem, readItem, updateItem, deleteItem, separator, eventsItem, teamsItem, gamesItem);

            // Attach the context menu to the root pane
            root.setOnContextMenuRequested(event -> 
                contextMenu.show(root, event.getScreenX(), event.getScreenY()));
            
            btnAddRow.setOnAction(event -> {
                 try {
                    addEmptyGame();
                 } catch (CreateException e) {
                    // Handle exception appropriately (e.g., show an error message)
                    e.printStackTrace();
                 }
});
            
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

    public void addEmptyGame() throws CreateException {
    try {
                
        Game newGame = new Game(); // Create an empty game
        newGame.setName("Default Name");
        newGame.setGenre("Default Genre");
        newGame.setPlatform("Default Platform");
        newGame.setPVPType(PVPType.TEAM_BASED_5V5);
        
        // Set default values or leave them empty, depending on your requirements
        newGame.setReleaseDate(null); // Or set a default release date
        // Set other properties as needed
        // Add the new game to the database
        gameManager.createGame(newGame);
        
        //gamesData.add(newGame);
        
        gamesData.clear();
        gamesData = FXCollections.observableArrayList(gameManager.getAllGames());
        tbGames.setItems(gamesData);    
        tbGames.refresh();
    } catch (Exception e) {
        throw new CreateException("Failed to add an empty game: " + e.getMessage());
    }
    }   
     private void deleteSelectedItem() {
        Game selectedGame = (Game)tbGames.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            try {
                // Eliminar el juego de la base de datos
                gameManager.deleteGame(selectedGame.getId());

                // Eliminar el juego de la lista observable y la tabla
                gamesData.remove(selectedGame);
                 // Refresh the TableView to reflect the changes
                
                tbGames.refresh();

            } catch (Exception e) {
                // Manejar la excepción apropiadamente (por ejemplo, mostrar un mensaje de error)
                e.printStackTrace();
            }
        }
    }
    // You can add more methods to handle other events or perform specific actions

}
