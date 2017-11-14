package nz.zephire.exceptions.exceptions

import org.joda.time.LocalTime

class RosteredException(start: LocalTime, end: LocalTime, description: String) : Exception(start, end, "ROSTERED", "ROSTERED", description) {

    var isComplete = false
}
