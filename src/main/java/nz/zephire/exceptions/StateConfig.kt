package nz.zephire.exceptions

import com.google.common.base.Charsets
import com.google.common.collect.Sets
import com.google.gson.annotations.SerializedName
import nz.zephire.exceptions.exceptions.Exception
import nz.zephire.exceptions.exceptions.PreApprovedException
import org.joda.time.LocalTime
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

val SAVE_LOCATION: Path = Paths.get(System.getProperty("user.home"), ".config", "exceptions.conf")

private val GSON = Utils.getGson()

fun loadConfig(): StateConfig {
    try {
        return GSON.fromJson(Files.newBufferedReader(SAVE_LOCATION, Charsets.UTF_8), StateConfig::class.java)
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

    var startTime: LocalTime? = null
        set(startTime) {
            field = startTime
            saveConfig()
        }

    var endTime: LocalTime? = null
        set(endTime) {
            field = endTime
            saveConfig()
        }

    var isFinished = true
        set(finished) {
            field = finished
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

    private fun saveConfig() {
        try {
            GSON.toJson(this, Files.newBufferedWriter(SAVE_LOCATION, Charsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
        } catch (e: IOException) {
            Utils.debugLog("Unable to save config!")
            Utils.debugLog(e)
            Utils.debugLog("")
            Utils.debugLog("State at save:")
            Utils.debugLog(this.toString())
            throw RuntimeException(e)
        }

    }

    override fun toString(): String {
        return "StateConfig{" +
                "username='" + this.username + '\'' +
                ", startTime=" + this.startTime +
                ", endTime=" + this.endTime +
                '}'
    }
}
