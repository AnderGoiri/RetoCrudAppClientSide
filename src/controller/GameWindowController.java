package controller;

import exceptions.BusinessLogicException;
import exceptions.EmptyGameAlreadyAddedException;
import exceptions.MaxCharException;
import exceptions.NameAlreadyExistsException;
import exceptions.WrongFormatException;
import extra.DatePickerCellGame;
import factory.GameFactory;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javax.ejb.CreateException;
import model.Game;
import model.PVPType;
import model.User;
import javafx.scene.input.KeyCode;

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

    private MenuItem createItem;
    private MenuItem readItem;
    @FXML
    private MenuItem deleteItem;
    private MenuItem eventsItem;
    private MenuItem teamsItem;
    private MenuItem gamesItem;

    /**
     * Method for initializing GestionUsuarios Stage.
     *
     * @param root The Parent object representing root node of view graph.
     */
    public void initStage(Parent root) {
        try {
            getScene().setRoot(root);

            stage.setTitle("Gestion de Juegos");
            stage.setResizable(false);

            //Set department combo data model.
            // Create a combo box
            cmbSearch.setOnAction(event -> {
                String selectedNamedQuery = (String) cmbSearch.getSelectionModel().getSelectedItem();

                handleComboBoxSelection(selectedNamedQuery);
            });

            // Add named queries to the combo box
            ObservableList<String> namedQueriesList = FXCollections.observableArrayList(
                    "findAllGames",
                    "findGamesByName",
                    "findGamesByGenre",
                    "findGamesByPlatform",
                    "findGamesByPVPType"
            );

            cmbSearch.setItems(namedQueriesList);
            cmbSearch.setValue("findAllGames");

            // Obtén los valores del enum y conviértelos a una lista observable
            ObservableList<PVPType> pvpTypes = FXCollections.observableArrayList(PVPType.values());
            // Asigna la lista de valores al ComboBox
            cmbPVPType.setItems(pvpTypes);

            //establecer un valor predeterminado
            cmbPVPType.setValue(PVPType.TEAM_BASED_5V5);

            //manejar eventos de selección si es necesario
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
                            lblError.setVisible(false);
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
                                    tbGames.refresh();

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
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "BusinessLogicException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("Ocurrió algun error en la capa de lógica");
                        } catch (WrongFormatException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "WrongFormatException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("En el nombre solo se permiten escribir letras");
                        } catch (MaxCharException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "MaxCharException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("LLegaste al máximo de carácteres");
                        } catch (NameAlreadyExistsException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "NameAlreadyExistsException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("El nombre de este juego ya está registrado");
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
                            lblError.setVisible(false);
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
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setGenre(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "BusinessLogicException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("Ocurrió algun error en la capa de lógica");
                        } catch (MaxCharException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setGenre(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "MaxCharException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("LLegaste al máximo de carácteres");
                        } catch (WrongFormatException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setGenre(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "WrongFormatException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("En el género solo se perimiten escribir letras");
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
                            lblError.setVisible(false);
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
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setPlatform(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "BusinessLogicException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("Ocurrió algun error en la capa de lógica");
                        } catch (MaxCharException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setPlatform(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "MaxCharException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("LLegaste al máximo de carácteres");
                        } catch (WrongFormatException ex) {
                            ((Game) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setPlatform(t.getOldValue());
                            tbGames.refresh();
                            LOGGER.log(Level.WARNING, "WrongFormatException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("En la plataforma solo se perimite escribir letras y números");
                        }
                    }
            );
            tbcolPVPType.setCellValueFactory(
                    new PropertyValueFactory<>("PVPType"));
            tbcolPVPType.setCellFactory(ComboBoxTableCell.forTableColumn(PVPType.values()));
            tbcolPVPType.setOnEditCommit(
                    (TableColumn.CellEditEvent<Game, PVPType> t) -> {
                        try {
                            lblError.setVisible(false);
                            ((Game) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPVPType(t.getNewValue());
                            Object selectedGame = tbGames.getSelectionModel().getSelectedItem();
                            GameFactory.getGameManager().updateGame(selectedGame);
                        } catch (BusinessLogicException ex) {
                            LOGGER.log(Level.WARNING, "BusinessLogicException: ", ex.getMessage());
                            lblError.setVisible(true);
                            lblError.setText("Ocurrió algun error en la capa de lógica");
                        }

                    });
            tbcolReleaseDate.setCellValueFactory(
                    new PropertyValueFactory<>("releaseDate"));
            tbcolReleaseDate.setCellFactory(column -> new DatePickerCellGame());

            tbcolReleaseDate.setOnEditCommit((TableColumn.CellEditEvent<Game, Date> t) -> {
                try {
                    lblError.setVisible(false);
                    // Set the parsed date as the new value
                    ((Game) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReleaseDate(t.getNewValue());

                    gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());

                    // Call the update method using the factory
                    GameFactory.getGameManager().updateGame(tbGames.getSelectionModel().getSelectedItem());

                } catch (BusinessLogicException ex) {
                    ((Game) t.getTableView().getItems().get(t.getTablePosition().getRow())).setReleaseDate(t.getOldValue());
                    LOGGER.log(Level.WARNING, "Exception: ", ex.getMessage());
                    lblError.setVisible(true);
                    lblError.setText("Ocurrió algún error en la capa de lógica");
                }
            });

            //Create an obsrvable list for users table.
            gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
            //Set table model.            
            tbGames.setEditable(true);

            tbGames.setItems(gamesData);

            // Create a context menu
            ContextMenu contextMenu = new ContextMenu();

            // CRUD options
            createItem = new MenuItem("    Crear juego");
            readItem = new MenuItem("    Read Game");
            deleteItem = new MenuItem("    Delete Game");
            eventsItem = new MenuItem("    Events");
            teamsItem = new MenuItem("    Teams");
            gamesItem = new MenuItem("    Games");

            // Separator
            SeparatorMenuItem separator = new SeparatorMenuItem();

            //TODO
            // Add actions to CRUD options (you can customize these actions)
            createItem.setOnAction(e -> {
                try {
                    lblError.setVisible(false);
                    addEmptyGame();
                } catch (CreateException ex) {
                    LOGGER.log(Level.WARNING, "CreateException: ", ex.getMessage());
                    lblError.setVisible(true);
                    lblError.setText("Ocurrió un error al añadir un juego nuevo");
                } catch (EmptyGameAlreadyAddedException ex) {
                    LOGGER.log(Level.WARNING, "EmptyGameAlreadyAddedException: ", ex.getMessage());
                    lblError.setVisible(true);
                    lblError.setText("Un juego vacío ya ha sido añadido, modifiquelo para añadir otro");
                }
            });
            readItem.setOnAction(e -> handleSearchButton());
            deleteItem.setOnAction(e -> deleteSelectedItem());

            // Add CRUD options to the context menu
            contextMenu.getItems().addAll(createItem, readItem, deleteItem);

            // Attach the context menu to the root pane
            root.setOnContextMenuRequested(event
                    -> contextMenu.show(root, event.getScreenX(), event.getScreenY()));

            scene.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.DELETE) {
                    deleteSelectedItem();
                }
            });

            btnAddRow.setOnAction(event -> {
                try {
                    lblError.setVisible(false);
                    addEmptyGame();
                } catch (CreateException ex) {
                    LOGGER.log(Level.WARNING, "CreateException: ", ex.getMessage());
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
            if (!(loggedUser.getUser_type().equalsIgnoreCase("Admin"))) {
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

            setTbGames(tbGames);
            stage.setScene(scene);
            //Show window.
            stage.show();
        } catch (Exception e) {
            showErrorAlert("No se ha podido abrir la ventana.\n"
                    + e.getMessage());
        }
    }

    /**
     * Method to add an empty game to the table.
     *
     * @throws CreateException If an error occurs during game creation.
     * @throws EmptyGameAlreadyAddedException If an empty game is already added.
     */
    public void addEmptyGame() throws CreateException, EmptyGameAlreadyAddedException {
        try {

            gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
            // Create an empty game
            Game newGame = new Game();
            newGame.setName("Default");
            newGame.setGenre("Default");
            newGame.setPlatform("Default");
            newGame.setPVPType(PVPType.TEAM_BASED_5V5);
            newGame.setReleaseDate(null);
            //check if there is any empty game already on the table

            if (!gamesData.get(gamesData.size() - 1).equals(newGame) || gamesData.isEmpty()) {

                GameFactory.getGameManager().createGame(newGame);
                gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
                tbGames.refresh();
                tbGames.setItems(gamesData);
            } else {
                throw new EmptyGameAlreadyAddedException("Email alreaady added");
            }

        } catch (BusinessLogicException ex) {
            LOGGER.log(Level.WARNING, "BusinessLogicException: ", ex.getMessage());
            lblError.setVisible(true);
            lblError.setText("Ocurrió algún error en la capa de lógica");
        }
    }

    /**
     * Deletes the selected item from the table and the database.
     */
    private void deleteSelectedItem() {
        Game selectedGame = (Game) tbGames.getSelectionModel().getSelectedItem();

        if (selectedGame != null) {
            try {
                // Delete game from database
                gameManager.deleteGame(selectedGame.getId());

                // Get games to an onservable list, refresh and set items to the table
                gamesData = FXCollections.observableArrayList(GameFactory.getGameManager().getAllGames());
                tbGames.refresh();
                tbGames.setItems(gamesData);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(GameWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Handles the selection of the named query in the combo box.
     *
     * @param selectedNamedQuery The selected named query.
     */
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
            default:
                break;
        }
    }

    /**
     * Handles the selection of PVPType in the combo box.
     */
    private void handlePVPTypeSelection() {
        //set combo box PVPType
        PVPType selectedPVPType = cmbPVPType.getValue();
    }

    /**
     * Handles the search button click event.
     */
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
