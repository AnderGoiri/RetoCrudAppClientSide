package controller;

import businessLogic.BusinessLogicException;
import businessLogic.ESportsFactory;
import businessLogic.GameManager;
import exceptions.EmptyGameAlreadyAddedException;
import exceptions.MaxCharException;
import exceptions.NameAlreadyExistsException;
import exceptions.NameException;
import exceptions.WrongFormatException;
import extra.DatePickerCellGame;
import factory.GameFactory;
//import static groovy.util.GroovyTestCase.assertEquals;
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
import java.lang.String;
import java.util.List;
import javax.naming.OperationNotSupportedException;

public class GameWindowController extends GenericController {

    private final static Logger LOGGER = Logger.getLogger(GameWindowController.class.getName());
    
    @FXML
    private HBox hBoxMenu;

    @FXML
    private TableView<Game> tbGames;
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
    private Label lblData;

    @FXML
    private Label lblError;

    @FXML
    private Button btnExit;

    @FXML
    private ComboBox cmbSearch;

    @FXML
    private TextField tfSearchData;

    @FXML
    private ComboBox<PVPType> cmbPVPType;

    @FXML
    private DatePicker dpReleaseDate;

    @FXML
    private Button btnAddRow;

    @FXML
    private Button btnSearch;

    @FXML
    private User loggedUser;
    /**
     * User's table data model.
     */
    private ObservableList<Game> gamesData;

    /**
     * Method for initializing GestionUsuarios Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            stage = new Stage();
            //Set stage properties
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.setScene(scene);
            stage.setTitle("Gestion de Juegos");
            stage.setResizable(false);

            //Obtains the layout containing the menu bar from the scene node graph
            HBox hBoxMenu = (HBox) root.getChildrenUnmodifiable().get(0);
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
            );
            //Set department combo data model.
            // Create a combo box

            cmbSearch.setOnAction(event -> {
                String selectedNamedQuery = (String) cmbSearch.getSelectionModel().getSelectedItem();

                handleComboBoxSelection(selectedNamedQuery);
                //handleComboBoxSelection();
            });

            // Add named queries to the combo box
            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    "findAllGames",
                    "findGamesByName",
                    "findGamesByGenre",
                    "findGamesByPlatform",
                    "findGamesByPVPType",
                    "findGamesByReleaseDate",
                    "findAllGamesCreatedByAdmin"
            );

            cmbSearch.setItems(namedQueriesList);
            cmbSearch.setValue("findAllGames");

            // Obtén los valores del enum y conviértelos a una lista observable
            ObservableList<PVPType> pvpTypes = FXCollections.observableArrayList(PVPType.values());
            // Asigna la lista de valores al ComboBox
            cmbPVPType.setItems(pvpTypes);

            // Puedes establecer un valor predeterminado si es necesario
            cmbPVPType.setValue(PVPType.TEAM_BASED_5V5);

            // Puedes manejar eventos de selección si es necesario
            cmbPVPType.setOnAction(event -> handlePVPTypeSelection());
            btnSearch.setOnAction(event -> handleSearchButton());

            tbcolId.setCellValueFactory(
                    new PropertyValueFactory<>("id"));

            tbcolName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            tbcolName.setCellFactory(
                    TextFieldTableCell.forTableColumn());
            tbcolName.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, String> t) -> {
                        try {
                            String regexLetters = "^[a-zA-Z]+$";
                            if (t.getNewValue().matches(regexLetters)) {
                                if (t.getNewValue().length() < 100) {
                                    ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getNewValue());

                                    //change the old value for the new value
                                    gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());

                                    //call the update method using the facotry 
                                    GameFactory.getGameManager().updateGame(tbGames.getSelectionModel().getSelectedItem());

                                    for (Game game : gamesData) {
                                        if (game.getName() != null) {
                                            if (game.getName().equals(t.getNewValue())) {
                                                throw new NameAlreadyExistsException();
                                            }
                                        }
                                    }
                                } else {
                                    throw new MaxCharException();
                                }
                            } else {
                                throw new WrongFormatException();
                            }

                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (WrongFormatException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MaxCharException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NameAlreadyExistsException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );
            tbcolGenre.setCellValueFactory(
                    new PropertyValueFactory<>("genre"));
            tbcolGenre.setCellFactory(
                    TextFieldTableCell.forTableColumn());
            tbcolGenre.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, String> t) -> {
                        try {
                            String regexLetters = "^[a-zA-Z]+$";
                            if (t.getNewValue().matches(regexLetters)) {
                                if (t.getNewValue().length() < 100) {
                                    ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setGenre(t.getNewValue());

                                    //change the old value for the new value
                                    gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());

                                    //call the update method using the facotry 
                                    GameFactory.getGameManager().updateGame(tbGames.getSelectionModel().getSelectedItem());

                                } else {
                                    throw new MaxCharException();
                                }
                            } else {
                                throw new WrongFormatException();
                            }

                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MaxCharException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (WrongFormatException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );

            tbcolPlatform.setCellValueFactory(
                    new PropertyValueFactory<>("platform"));
            tbcolPlatform.setCellFactory(
                    TextFieldTableCell.forTableColumn());
            tbcolPlatform.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, String> t) -> {
                        try {
                            String regexLetters = "^[a-zA-Z0-9 ]+$";
                            if (t.getNewValue().matches(regexLetters)) {
                                if (t.getNewValue().length() < 100) {
                                    ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setPlatform(t.getNewValue());

                                    //change the old value for the new value
                                    gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());

                                    //call the update method using the facotry 
                                    GameFactory.getGameManager().updateGame(tbGames.getSelectionModel().getSelectedItem());

                                } else {
                                    throw new MaxCharException();
                                }
                            } else {
                                throw new WrongFormatException();
                            }

                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (MaxCharException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (WrongFormatException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );
            tbcolPVPType.setCellValueFactory(
                    new PropertyValueFactory<>("PVPType"));
            tbcolPVPType.setCellFactory(ComboBoxTableCell.forTableColumn(PVPType.values()));
            tbcolPVPType.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, PVPType> t) -> {
                        try {

                            ((Game) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPVPType(t.getNewValue());
                            Object selectedGame = tbGames.getSelectionModel().getSelectedItem();
                            //String gameID = String.valueOf(((Game)tbGames.getSelectionModel().getSelectedItem()).getId());
                            GameFactory.getGameManager().updateGame(selectedGame);
                        } catch (BusinessLogicException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
            tbcolReleaseDate.setCellValueFactory(
                    new PropertyValueFactory<>("releaseDate"));
            tbcolReleaseDate.setCellFactory(column -> new DatePickerCellGame());

            tbcolReleaseDate.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, Date> t) -> {
                        try {
                            //change the old value for the new value
                            ((Game) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setReleaseDate(t.getNewValue());

                            gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());

                            //call the update method using the facotry 
                             GameFactory.getGameManager().updateGame(tbGames.getSelectionModel().getSelectedItem());

                        }catch (BusinessLogicException ex) {
                            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            );

            //Create an obsrvable list for users table.
            gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
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
            createItem.setOnAction(e -> {
                try {
                    addEmptyGame();
                } catch (CreateException ex) {
                    Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (EmptyGameAlreadyAddedException ex) {
                    Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            readItem.setOnAction(e -> handleSearchButton());
            //updateItem.setOnAction(e -> System.out.println("Update action"));
            deleteItem.setOnAction(e -> deleteSelectedItem());
            eventsItem.setOnAction(e -> System.out.println("Events action"));
            teamsItem.setOnAction(e -> System.out.println("Teams action"));
            gamesItem.setOnAction(e -> System.out.println("Games action"));

            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(createItem, readItem, deleteItem, separator, eventsItem, teamsItem, gamesItem);

            // Attach the context menu to the root pane
            root.setOnContextMenuRequested(event
                    -> contextMenu.show(root, event.getScreenX(), event.getScreenY()));

            btnAddRow.setOnAction(event -> {
                try {
                    lblError.setVisible(false);
                    addEmptyGame();
                } catch (CreateException ex) {
                    LOGGER.log(Level.WARNING, "CreateException: ", ex.getMessage());
                    Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    lblError.setVisible(true);
                    lblError.setText("Ocurrió un error al añadir un juego nuevo");
                } catch (EmptyGameAlreadyAddedException ex) {
                    LOGGER.log(Level.WARNING, "EmptyGameAlreadyAddedException: ", ex.getMessage());
                    lblError.setVisible(true);
                    lblError.setText("Un juego vacío ya ha sido añadido, modifiquelo para añadir otro");
                }

            });
            tfSearchData.setVisible(false);
            dpReleaseDate.setVisible(false);
            cmbPVPType.setVisible(false);
            lblData.setVisible(false);

            lblError.setVisible(false);

            loggedUser = new User();
            loggedUser.setUser_type("Admin");
            if (!(loggedUser.getUser_type() == "Admin")) {
                btnAddRow.setDisable(true);
                deleteItem.setDisable(true);
                createItem.setDisable(true);
            }

            btnSearch.setDefaultButton(true);
            btnExit.setCancelButton(true);

            // Closing the window
            stage.setOnCloseRequest(event -> super.handleCloseRequest(event));
            //TODO Change it to go back to login
            btnExit.setOnAction(event -> super.handleBtnClose(event));

            stage.setScene(scene);
            //Show window.
            stage.show();
        } catch (Exception e) {
            showErrorAlert("No se ha podido abrir la ventana.\n"
                    + e.getMessage());
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

    public void addEmptyGame() throws CreateException, EmptyGameAlreadyAddedException {
        try {

            gamesData = FXCollections.observableArrayList( GameFactory.getGameManager().getAllGames());

            // Create an empty game
            Game newGame = new Game(); 
            newGame.setName("Default");
            newGame.setGenre("Default");
            newGame.setPlatform("Default");
            newGame.setPVPType(PVPType.TEAM_BASED_5V5);
            newGame.setReleaseDate(null);
            //check if there is any empty game already on the table

            if (!gamesData.isEmpty()) {
                if (!gamesData.get(gamesData.size() - 1).equals(newGame)) {

                    GameFactory.getGameManager().createGame(newGame);
                    gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
                    tbGames.refresh();
                    tbGames.setItems(gamesData);
                } else{
                    throw new EmptyGameAlreadyAddedException("Email alreaady added");
                }
            }else{
                 GameFactory.getGameManager().createGame(newGame);
            }
        }  catch (BusinessLogicException ex) {
            Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteSelectedItem() {
        Game selectedGame = (Game) tbGames.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            try {
                // Eliminar el juego de la base de datos
                gameManager.deleteGame(selectedGame.getId());

                // Eliminar el juego de la lista observable y la tabla
                gamesData.remove(selectedGame);
                // Refresh the TableView to reflect the changes

                //tbGames.refresh();

            } catch (Exception e) {
                // Manejar la excepción apropiadamente (por ejemplo, mostrar un mensaje de error)
                e.printStackTrace();
            }
        }
    }

    private void handleComboBoxSelection(String selectedNamedQuery) {
        //String selectedNamedQuery = (String) cmbSearch.getSelectionModel().getSelectedItem();
        // Hide all text fields initially      
        tfSearchData.setVisible(false);
        dpReleaseDate.setVisible(false);
        cmbPVPType.setVisible(false);
        lblData.setVisible(false);
        // Show text fields based on the selected query
        switch (selectedNamedQuery) {
            case "findGamesByName":
                lblData.setText("Nombre");
                lblData.setVisible(true);
                tfSearchData.setVisible(true);
                break;
            case "findGamesByGenre":
                lblData.setText("Género");
                lblData.setVisible(true);
                tfSearchData.setVisible(true);
                break;
            case "findGamesByPlatform":
                lblData.setText("Plataforma");
                lblData.setVisible(true);
                tfSearchData.setVisible(true);
                break;
            case "findGamesByPVPType":
                lblData.setText("Tipo de PVP");
                lblData.setVisible(true);
                cmbPVPType.setVisible(true);
                break;
            case "findGamesByReleaseDate":
                lblData.setText("Fecha de Lanzamiento");
                lblData.setVisible(true);
                dpReleaseDate.setVisible(true);
                break;
            case "findAllGamesCreatedByAdmin":
                lblData.setText("Nombre de usuario de Admin");
                lblData.setVisible(true);
                tfSearchData.setVisible(true);
                break;
            default:
                break;
        }
    }

    private void handlePVPTypeSelection() {
        //set combo box PVPType
        PVPType selectedPVPType = cmbPVPType.getValue();
    }

    @FXML
    private void handleSearchButton() {
        try {
            // Obtén la consulta seleccionada de la combo
            String selectedNamedQuery = (String) cmbSearch.getValue();
            gamesData.clear();
            switch (selectedNamedQuery) {
                case "findGamesByName":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findByName(tfSearchData.getText()));
                    break;
                case "findGamesByGenre":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findByGenre(tfSearchData.getText()));
                    break;
                case "findGamesByPlatform":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findByPlatform(tfSearchData.getText()));
                    break;
                case "findGamesByPVPType":
                    String pvpTypeStr = cmbPVPType.getValue().toString();
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findByPVPType(pvpTypeStr));
                    break;
                case "findGamesByReleaseDate":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findByReleaseDate(dpReleaseDate.getValue().toString()));
                    break;
                case "findAllGamesCreatedByAdmin":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().findGamesCreatedByAdmin(tfSearchData.getText()));
                    break;
                case "findAllGames":
                    gamesData = FXCollections.observableArrayList(
                            GameFactory.getGameManager().getAllGames());
                    break;
                default:
                    break;
            }

            tbGames.setItems(gamesData);
            tbGames.refresh();
            // Aquí puedes realizar otras acciones según tus requisitos
        } catch (Exception e) {
            // Maneja la excepción apropiadamente (por ejemplo, muestra un mensaje de error)
            e.printStackTrace();
        }
    }
}
