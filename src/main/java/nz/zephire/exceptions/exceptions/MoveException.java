package nz.zephire.exceptions.exceptions;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class MoveException extends Exception {

    private final Exception preApprovedException;

    public MoveException(LocalDate date, LocalTime start, LocalTime end,
            String approver, String reason, String description,
            Exception preApprovedException) {

        super(date, start, end, approver, reason, description);


        this.preApprovedException = preApprovedException;
    }

    public Exception getPreApprovedException() {
        return preApprovedException;
    }
}
