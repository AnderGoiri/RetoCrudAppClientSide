/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GenericController.LOGGER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.WindowEvent;
import model.Event;

/**
 * FXML Controller class
 *
 * @author Ander Goirigolzarri Iturburu
 */
public class EventsViewController extends GenericController {

    @FXML
    private Label lbVentana, lbBusqueda, lbNombre, lbJuego, lbLugar, lbONG, lbPremio, lbDonacion, lbAforo, lbFecha, lbError, lbEquipo;
    @FXML
    private ComboBox<?> cbBusqueda, cbJuego, cbEquipo;
    @FXML
    private TextField tfNombre, tfLugar, tfONG, tfPremio, tfDonacion, tfAforo;
    @FXML
    private DatePicker dpFecha;
    @FXML
    private Button btnBuscar, btnLimpiar, btnCrear, btnModificar, btnEliminar, btnSalir, btnUnirse;
    @FXML
    private ImageView imgBuscar, imgLimpiar;
    @FXML
    private TableView<?> tableViewEvents;
    @FXML
    private TableColumn<?, ?> columnNombre, columnJuego, columnLugar, columnFecha, columnAforo, columnONG, columnPremio, columnDonacion, columnGanador;

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    public void initStage(Parent root) {
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

            //Cargar los datos del combobox Juegos
            ObservableList<String> gameNames = FXCollections.observableArrayList();
            //gameNames.stream().

            //cbJuego.setItems(gameNames);
            tableViewEvents.setEditable(false);

            lbError.setVisible(false);

            btnBuscar.setDefaultButton(true);

            //Setting Event TableView cell values
            columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnJuego.setCellValueFactory(new PropertyValueFactory<>("juego"));
            columnLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));
            columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
            columnAforo.setCellValueFactory(new PropertyValueFactory<>("aforo"));
            columnONG.setCellValueFactory(new PropertyValueFactory<>("ong"));
            columnPremio.setCellValueFactory(new PropertyValueFactory<>("premio"));
            columnDonacion.setCellValueFactory(new PropertyValueFactory<>("donacion"));
            columnGanador.setCellValueFactory(new PropertyValueFactory<>("ganador"));

            ObservableList<Event> events = FXCollections.observableArrayList(eventManager.findAllEvents());

            stage.show();
        } catch (Exception e) {
            showErrorAlert("No se ha podido abrir la ventana.\n"
                    + e.getMessage());
        }
    }
}
