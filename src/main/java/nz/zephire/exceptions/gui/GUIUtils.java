package nz.zephire.exceptions.gui;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.Calendar;

import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.text.DateFormatter;

public class GUIUtils {

    public static final Border TEXT_BORDER = new JTextField().getBorder();

    public static final DateTimeFormatter TIME_FORMAT = new DateTimeFormatterBuilder()
            .appendHourOfDay(2)
            .appendLiteral(":")
            .appendMinuteOfHour(2)
            .toFormatter();

    public static JSpinner createTimeSpinner(LocalTime value) {
        JSpinner spinner = new JSpinner();
        SpinnerDateModel model = new SpinnerDateModel();

        model.setCalendarField(Calendar.MINUTE);
        model.setValue(value.toDateTimeToday().toDate());
        spinner.setModel(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner , "HH:mm");
        DateFormatter formatter = (DateFormatter)editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);

        return spinner;
    }

}
