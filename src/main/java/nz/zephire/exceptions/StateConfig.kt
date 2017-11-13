package nz.zephire.exceptions

import com.google.common.collect.Sets
import com.google.gson.annotations.SerializedName
import nz.zephire.exceptions.exceptions.Exception
import nz.zephire.exceptions.exceptions.PreApprovedException
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

    var previousStartTime: LocalTime = LocalTime.MIDNIGHT
        set(value) {
            field = value
            saveConfig()
        }

    var previousEndTime: LocalTime = LocalTime.MIDNIGHT
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


    @SerializedName("pre_approved")
    private var _preApproved: MutableSet<PreApprovedException> = Sets.newHashSet()

    @Transient
    val preApproved: Set<PreApprovedException> = Collections.unmodifiableSet(_preApproved)

    @SerializedName("exceptions")
    private var _exceptions: MutableSet<Exception> = Sets.newHashSet()

    @Transient
    val exceptions: Set<Exception> = Collections.unmodifiableSet(_exceptions)

    fun addPreApproved(preApproved: PreApprovedException) {
        this._preApproved.add(preApproved)
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
                "previousStartTime=$previousStartTime, " +
                "previousEndTime=$previousEndTime, " +
                "previousDay=$previousDay, " +
                "isFinished=$isFinished, " +
                "preApproved=$preApproved, " +
                "exceptions=$exceptions" +
                ")"
    }
}
