package nz.zephire.exceptions.exceptions

import org.joda.time.LocalDate
import org.joda.time.LocalTime

open class Exception(val date: LocalDate, val start: LocalTime, val end: LocalTime,
                     val approver: String, val reason: String, val description: String)
