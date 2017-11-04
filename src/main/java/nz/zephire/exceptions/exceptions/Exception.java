package nz.zephire.exceptions.exceptions;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class Exception {

    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final String approver;
    private final String reason;
    private final String description;

    public Exception(LocalDate date, LocalTime start, LocalTime end,
            String approver, String reason, String description) {

        this.date = date;
        this.start = start;
        this.end = end;
        this.approver = approver;
        this.reason = reason;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public String getApprover() {
        return approver;
    }

    public String getReason() {
        return reason;
    }

    public String getDescription() {
        return description;
    }
}
