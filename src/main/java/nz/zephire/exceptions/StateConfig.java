package nz.zephire.exceptions;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import nz.zephire.exceptions.exceptions.Exception;
import nz.zephire.exceptions.exceptions.PreApprovedException;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds persisted state information, and saves/loads to ensure state is transferred across restarts.
 */
public class StateConfig {

    public static final transient Path SAVE_LOCATION = Paths.get(System.getProperty("user.home"),".config", "exceptions.conf");

    private static transient Gson gson = Utils.getGson();

    private String username;

    private LocalTime startTime;

    private LocalTime endTime;

    private boolean isFinished = true;

    private Set<PreApprovedException> preApproved = new HashSet<>();

    private Set<Exception> exceptions = new HashSet<>();

    //TODO: Exceptions

    public static StateConfig loadConfig() {
        try {
            return gson.fromJson(Files.newBufferedReader(SAVE_LOCATION, Charsets.UTF_8), StateConfig.class);
        } catch (IOException e) {
            Utils.debugLog("Unable to load config!");
            Utils.debugLog(e);
            throw new RuntimeException(e);
        }
    }

    public void saveConfig() {
        try {
            gson.toJson(this, Files.newBufferedWriter(SAVE_LOCATION, Charsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING));
        } catch (IOException e) {
            Utils.debugLog("Unable to save config!");
            Utils.debugLog(e);
            Utils.debugLog("");
            Utils.debugLog("State at save:");
            Utils.debugLog(this.toString());
            throw new RuntimeException(e);
        }
    }

    public String getUsername() {
        return username;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Set<PreApprovedException> getPreApproved() {
        return Collections.unmodifiableSet(preApproved);
    }

    public Set<Exception> getExceptions() {
        return Collections.unmodifiableSet(exceptions);
    }

    public void setUsername(String username) {
        this.username = username;
        saveConfig();
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        saveConfig();
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        saveConfig();
    }

    public void setFinished(boolean finished) {
        this.isFinished = finished;
        saveConfig();
    }

    public void addPreApproved(PreApprovedException preApproved) {
        this.preApproved.add(preApproved);
        saveConfig();
    }

    public void addException(Exception exception) {
        this.exceptions.add(exception);
        saveConfig();
    }

    @Override
    public String toString() {
        return "StateConfig{" +
                "username='" + username + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
