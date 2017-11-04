package nz.zephire.exceptions.postback.backend;

import nz.zephire.exceptions.postback.SPFormData;

import java.io.IOException;

public interface SPBackend {

    SPFormData getForm() throws IOException;

    void postForm(String body) throws IOException;

}
