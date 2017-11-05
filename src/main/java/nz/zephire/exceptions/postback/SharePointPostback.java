package nz.zephire.exceptions.postback;

import com.google.common.base.Joiner;
import com.google.gson.Gson;
import nz.zephire.exceptions.postback.backend.KBBackend;
import nz.zephire.exceptions.postback.backend.SPBackend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SharePointPostback implements SPPostbackObject {

    // URL to GET, provides the initial setup of the form
    public static final String INIT_URL = "http://2dintranet.2degreesmobile.co.nz/tools/requests/_layouts/FormServer.aspx?"
            + "XsnLocation=http://2dintranet.2degreesmobile.co.nz/tools/requests/Exceptions/Forms/template.xsn"
            + "&SaveLocation=http%3A%2F%2F2dintranet%2E2degreesmobile%2Eco%2Enz%2Ftools%2Frequests%2FExceptions";

    // URL to send the form to once complete
    public static final String POSTBACK_URL = "https://2dintranet.2degreesmobile.co.nz/tools/requests/_layouts/Postback.FormServer.aspx";

    // Locations of important parts in currentFormData object
    private static final int REQUEST_UUID = 3;
    private static final int TIMESTAMP_ZEROED = 18;
    private static final int CANARY = 37;

    // Described path through tree to find important parts
    private static final int[] APPROVERS_UNWRAP_SEQUENCE = new int[] {1, 1, 0, 0, 1, 2, 2, 1, 2};
    private final SPBackend backend;


    private List<SPPostbackObject> objects = new LinkedList<>();
    private SPFormData formData;

    public SharePointPostback() {
        this(new KBBackend());
    }

    public SharePointPostback(SPBackend backend) {

        this.backend = backend;
    }

    public void init() throws IOException {
        formData = backend.getForm();
    }

    public void post() throws IOException {
        backend.postForm(this.toString());
    }

    public void add(SPPostbackObject object) {
        objects.add(object);
    }

    public SPFormData getFormData() {
        return formData;
    }

    public String toString() {
        return Joiner.on(" ").join(objects);
    }

    /**
     * Finds the JSON variable representing form state, and returns it
     * @param html Unencoded HTML body
     * @return {@link SPFormData} representing important info in state
     */
    public static SPFormData parseFormData(String html) {
        Pattern regex = Pattern.compile("var g_objCurrentFormData_FormControl = (.*);");
        Matcher match = regex.matcher(html);

        return readFormData(match.group(1));
    }

    // Parses "JSON of var g_objCurrentFormData_FormControl"
    private static SPFormData readFormData(String formData) {
        List<?> obj = new Gson().fromJson(formData, List.class);

        // Navigates the nested arrays using given sequence
        List<?> curr = obj;
        for(int i : APPROVERS_UNWRAP_SEQUENCE) {
            curr = (List<?>) curr.get(i);
        }

        List<String> approvers = new ArrayList<>(curr.size());
        for(Object approver : curr) {
            if(!(approver instanceof String)) continue;

            approvers.add((String) approver);
        }

        return new SPFormData(
                (String) obj.get(REQUEST_UUID),
                (String)obj.get(TIMESTAMP_ZEROED),
                (String) obj.get(CANARY),
                approvers,
                obj
        );
    }
}
