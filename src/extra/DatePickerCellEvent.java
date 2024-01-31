package extra;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerCellEvent<Event> extends TableCell<Event, Date> {

    private final SimpleDateFormat dateFormat;

    public DatePickerCellEvent(String pattern) {
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

    public static <Event> Callback<TableColumn<Event, Date>, TableCell<Event, Date>> forTableColumn(String pattern) {
        return param -> new DatePickerCellEvent<>(pattern);
    }
}
