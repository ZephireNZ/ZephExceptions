package nz.zephire.exceptions.exceptions

import org.joda.time.LocalDate
import org.joda.time.LocalTime

class MoveException(date: LocalDate, start: LocalTime, end: LocalTime,
                    approver: String, reason: String, description: String,
                    val preApprovedException: Exception) : Exception(date, start, end, approver, reason, description)
