package nz.zephire.exceptions.exceptions;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.HashSet;
import java.util.Set;

public class ExceptionManager {

//    LocalTime a = new LocalTime(5,30);
//    LocalTime b = new LocalTime(6, 0);
//    Period p = new Period(a, b);
//    p.toStandardDuration().getStandardMinutes(); // TODO: length of exception/differences

    private final Set<PreApprovedException> preApproved = new HashSet<>();

    private final Set<Exception> exceptions = new HashSet<>();

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public ExceptionManager(LocalDate date, LocalTime startTime, LocalTime endTime) {

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Exception getNextPreApproved() {
        LocalTime now = LocalTime.now();

        Exception next = null;
        for(PreApprovedException e : preApproved) {
            if(e.isComplete()) continue;

            if(next == null) {;
                next = e;
            } else {
                if(next.getStart().compareTo(e.getStart()) > 0) { // >0 == earliest is later than e
                    next = e;
                }
            }
        }

        return next;
    }

    public void addException(Exception exception) {
        if(exception instanceof PreApprovedException) {
            preApproved.add((PreApprovedException) exception);
            return;
        }

        exceptions.add(exception);
    }

    // Simple Getters

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
