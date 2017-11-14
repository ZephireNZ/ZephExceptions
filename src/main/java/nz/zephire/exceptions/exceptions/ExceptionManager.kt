package nz.zephire.exceptions.exceptions

import nz.zephire.exceptions.StateConfig
import nz.zephire.exceptions.loadConfig

private var inst: ExceptionManager? = null

fun inst(): ExceptionManager {
    return inst ?: ExceptionManager(loadConfig())
}

class ExceptionManager internal constructor(val config: StateConfig) {

    val nextPreApproved: Exception?
        get() {
            var next: Exception? = null
            for (e in config.rostered) {
                if (e.isComplete) continue

                if (next == null) {
                    next = e
                } else {
                    if (e.start < next.start) {
                        next = e
                    }
                }
            }

            return next
        }

    /**
     * Fetches and gives the current adherence percentage
     * @return Adherence percentage between 0-100
     */
    val adherencePercent: Int
        get() = 95 //TODO: Fetch/Cache

    init {
        inst = this
    }

    fun addException(exception: Exception) {
        if (exception is RosteredException) {
            config.addRosteredException(exception)
            return
        }

        config.addException(exception)
    }
}
