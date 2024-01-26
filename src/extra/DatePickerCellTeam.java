/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extra;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerCellTeam<Team> extends TableCell<Team, Date> {
    private final SimpleDateFormat dateFormat;

    public DatePickerCellTeam(String pattern) {
        this.dateFormat = new SimpleDateFormat(pattern);
    }

    @Override
    protected void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(dateFormat.format(item));
        }
    }

    public static <Team> Callback<TableColumn<Team, Date>, TableCell<Team, Date>> forTableColumn(String pattern) {
        return param -> new DatePickerCellTeam<>(pattern);
    }
}
