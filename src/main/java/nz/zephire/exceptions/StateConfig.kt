package nz.zephire.exceptions

import com.google.common.collect.Lists
import com.google.gson.annotations.SerializedName
import nz.zephire.exceptions.exceptions.Exception
import nz.zephire.exceptions.exceptions.RosteredException
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.*

val SAVE_LOCATION: File = Paths.get(System.getProperty("user.home"), ".config", "exceptions.conf").toFile()

private val GSON = Utils.getGson()

fun loadConfig(): StateConfig {
    try {
        if(!SAVE_LOCATION.exists()) {
            val conf = StateConfig()
            conf.saveConfig()
            return conf
        }

        return GSON.fromJson(SAVE_LOCATION.bufferedReader(), StateConfig::class.java)
    } catch (e: IOException) {
        Utils.debugLog("Unable to load config!")
        Utils.debugLog(e)
        throw RuntimeException(e)
    }
}

/**
 * Holds persisted state information, and saves/loads to ensure state is transferred across restarts.
 */
class StateConfig {

    var username: String = ""
        set(username) {
            field = username
            saveConfig()
        }

    var startTime: LocalTime = LocalTime.MIDNIGHT
        set(value) {
            field = value
            saveConfig()
        }

    var endTime: LocalTime = LocalTime.MIDNIGHT
        set(value) {
            field = value
            saveConfig()
        }

    var previousDay: LocalDate = LocalDate.now()
        set(value) {
            field = value
            saveConfig()
        }

    var isFinished = true
        set(value) {
            field = value
            saveConfig()
        }

    var currentExceptionStartTime: LocalTime? = null
        set(value) {
            field = value
            saveConfig()
        }

    @SerializedName("pre_approved")
    private var _rostered: MutableList<RosteredException> = Lists.newArrayList()

    @Transient
    val rostered: List<RosteredException> = Collections.unmodifiableList(_rostered)

    @SerializedName("exceptions")
    private var _exceptions: MutableList<Exception> = Lists.newArrayList()

    @Transient
    val exceptions: List<Exception> = Collections.unmodifiableList(_exceptions)

    @SerializedName("current_rostered")
    private var _currentRostered: Int = -1 // Reference to list of Pre-Approved, or -1 for none
        set(value) {
            field = value
            saveConfig()
        }

    @Transient
    var currentRostered: RosteredException? = rostered.getOrNull(_currentRostered)

    fun addRosteredException(rostered: RosteredException) {
        this._rostered.add(rostered)
        saveConfig()
    }

    fun addException(exception: Exception) {
        this._exceptions.add(exception)
        saveConfig()
    }

    fun saveConfig() {
        val out = SAVE_LOCATION.bufferedWriter()

        try {
            GSON.toJson(this, out)
        } catch (e: IOException) {
            Utils.debugLog("Unable to save config!")
            Utils.debugLog(e)
            Utils.debugLog("")
            Utils.debugLog("State at save:")
            Utils.debugLog(this.toString())
            throw RuntimeException(e)
        } finally {
            out.close()
        }

    }

    override fun toString(): String {
        return "StateConfig(" +
                "username='$username', " +
                "startTime=$startTime, " +
                "endTime=$endTime, " +
                "previousDay=$previousDay, " +
                "isFinished=$isFinished, " +
                "currentExceptionStartTime=$currentExceptionStartTime, " +
                "rostered=$rostered, " +
                "exceptions=$exceptions" +
                ")"
    }
}
