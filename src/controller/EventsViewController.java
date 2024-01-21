/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.GenericController.LOGGER;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private TableColumn<?, ?> columnNombre, columnJuego, columnLugar, columnFecha, columnAforo, columnONG, columnPremio, columnDonacion, ColumnGanador;

    /**
     * Initializes the controller class.
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
            stage.setOnShowing(this::handleWindowShowing);

        } catch (Exception e) {
            showErrorAlert("No se ha podido abrir la ventana.\n"
                    + e.getMessage());
        }
    }

    /**
     * Initializes window state. It implements behavior for WINDOW_SHOWING type
     * event.
     *
     * @param event The window event
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning EventViewController::handleWindowShowing");
        btnUnirse.setDisable(true);
        btnBuscar.setDisable(true);
        btnCrear.setDisable(true);
        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
        cbEquipo.setDisable(true);
        //Cargar los datos del combobox Juegos

        tableViewEvents.setEditable(false);

        lbError.setVisible(false);

        btnBuscar.defaultButtonProperty();

    }

}
