package nz.zephire.exceptions.exceptions;

import nz.zephire.exceptions.StateConfig;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

public class ExceptionManager {

//    LocalTime a = new LocalTime(5,30);
//    LocalTime b = new LocalTime(6, 0);
//    Period p = new Period(a, b);
//    p.toStandardDuration().getStandardMinutes(); // TODO: length of exception/differences

    private final StateConfig config;
    private final LocalDate date = LocalDate.now();

    private static ExceptionManager inst;

    public static ExceptionManager inst() {
        if(inst == null) return new ExceptionManager(StateConfig.loadConfig());

        return inst;
    }

    private ExceptionManager(StateConfig config) {
        this.config = config;

        inst = this;
    }

    public Exception getNextPreApproved() {
        LocalTime now = LocalTime.now();

        Exception next = null;
        for(PreApprovedException e : config.getPreApproved()) {
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
            config.addPreApproved((PreApprovedException) exception);
            return;
        }

        config.addException(exception);
    }

    // Simple Getters

    public LocalDate getDate() {
        return date;
    }

    public StateConfig getConfig() {
        return config;
    }
}
