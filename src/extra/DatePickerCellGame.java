/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import model.Game;

/**
 *
 * @author Andoni Sanz
 */
public class DatePickerCellGame extends TableCell<Game, Date> {

    private DatePicker datePicker;

    public DatePickerCellGame() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.setOnAction((e) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }
    
    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);
        //The pattern for the date format should be read from a properties´ file
        SimpleDateFormat dateFormatter = new SimpleDateFormat(ResourceBundle.getBundle("config.parameters")
                          .getString("dateFormatPattern"));  
        
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(datePicker);
            } else if(item!=null){
                String date=dateFormatter.format(item);
                setText(date);
                setGraphic(null);
            }
        }
    }
    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
    }
}