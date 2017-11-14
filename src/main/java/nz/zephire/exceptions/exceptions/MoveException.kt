package nz.zephire.exceptions.exceptions

import org.joda.time.LocalTime

class MoveException(start: LocalTime, end: LocalTime, approver: String,
                    reason: String, description: String, val preApprovedException: Exception) : Exception(start, end, approver, reason, description)
