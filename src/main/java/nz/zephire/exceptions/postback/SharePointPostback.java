package nz.zephire.exceptions.postback;

import com.google.common.base.Joiner;
import nz.zephire.exceptions.postback.backend.KBBackend;
import nz.zephire.exceptions.postback.backend.SPBackend;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SharePointPostback implements SPPostbackObject {


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




}
