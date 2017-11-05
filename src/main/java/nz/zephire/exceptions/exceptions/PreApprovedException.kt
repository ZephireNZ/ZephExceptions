package nz.zephire.exceptions.exceptions

import org.joda.time.LocalDate
import org.joda.time.LocalTime

class PreApprovedException(date: LocalDate, start: LocalTime, end: LocalTime,
                           approver: String, reason: String, description: String) : Exception(date, start, end, approver, reason, description) {

    var isComplete = false
}
