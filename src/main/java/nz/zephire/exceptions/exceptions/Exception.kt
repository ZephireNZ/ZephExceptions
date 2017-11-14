package nz.zephire.exceptions.exceptions

import org.joda.time.Duration
import org.joda.time.LocalTime
import org.joda.time.Period

open class Exception(val start: LocalTime, val end: LocalTime,
                     val approver: String, val reason: String, val description: String) {

    //    LocalTime a = new LocalTime(5,30);
    //    LocalTime b = new LocalTime(6, 0);
    //    Period p = new Period(a, b);
    //    p.toStandardDuration().getStandardMinutes(); // TODO: length of exception/differences

    val length: Duration = Period(start, end).toStandardDuration()
}
