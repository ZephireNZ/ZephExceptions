package nz.zephire.exceptions.postback.backend;

import com.google.common.collect.Lists;
import nz.zephire.exceptions.Utils;
import nz.zephire.exceptions.postback.SPFormData;
import org.json.JSONArray;

import java.io.IOException;

// Used for testing, just returns static data
public class FakeBackend implements SPBackend {

    @Override
    public SPFormData getForm() throws IOException {
        Utils.debugLog("GET");
        return new SPFormData("bdbaf792c2d74b1d9c441d43db4a4645_63e2b16760f74bb4a2c715e4573eeaad",
                "636349282014412000",
                "uG2TEcJ9jnR0s96EWLWW4yL+UHIICLVz/AZ28jQ6G2EOzmyGUtClh8r/yDKYtEAW5loNRpY4D0VEd1DTIGzfRQ==|636439601974863152",
                Lists.newArrayList("Reina Sancho", "Deneshen Naidoo"),
                new JSONArray()
        );
    }

    @Override
    public void postForm(String body) throws IOException {
        Utils.debugLog("POST");
        Utils.debugLog(body);
        Utils.debugLog("");
    }
}
