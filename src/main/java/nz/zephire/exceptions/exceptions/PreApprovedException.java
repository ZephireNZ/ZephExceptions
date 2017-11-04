package nz.zephire.exceptions.exceptions;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class PreApprovedException extends Exception {

    private boolean complete = false;

    public PreApprovedException(LocalDate date, LocalTime start, LocalTime end,
            String approver, String reason, String description) {
        super(date, start, end, approver, reason, description);
    }

    public void markComplete() {
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
