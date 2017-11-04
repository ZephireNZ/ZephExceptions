package nz.zephire.exceptions.postback;

import org.json.JSONArray;

import java.util.List;

public class SPFormData {

    private final String uuid;
    private final String zeroedTimestamp;
    private final String canary;
    private final List<String> approvers;
    private final JSONArray raw;

    public SPFormData(String uuid, String zeroedTimestamp, String canary, List<String> approvers, JSONArray raw) {

        this.uuid = uuid;
        this.zeroedTimestamp = zeroedTimestamp;
        this.canary = canary;
        this.approvers = approvers;
        this.raw = raw;
    }

    public JSONArray getRaw() {
        return raw;
    }

    public String getUuid() {
        return uuid;
    }

    public String getZeroedTimestamp() {
        return zeroedTimestamp;
    }

    public String getCanary() {
        return canary;
    }

    public List<String> getApprovers() {
        return approvers;
    }
}
