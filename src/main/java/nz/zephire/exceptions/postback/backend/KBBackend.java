package nz.zephire.exceptions.postback.backend;

import static nz.zephire.exceptions.postback.SharePointPostback.INIT_URL;
import static nz.zephire.exceptions.postback.SharePointPostback.POSTBACK_URL;

import com.google.common.base.Joiner;
import com.google.common.io.CharStreams;
import nz.zephire.exceptions.Utils;
import nz.zephire.exceptions.postback.SPFormData;
import nz.zephire.exceptions.postback.SharePointPostback;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class KBBackend implements SPBackend {

    static {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
    }

    @Override
    public SPFormData getForm() throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(INIT_URL).openConnection();
        con.setRequestMethod("GET");

        String body = CharStreams.toString(new InputStreamReader(con.getInputStream()));

        Utils.debugLog("GET REQ HEADERS:");

        for (Map.Entry<String, List<String>> headerEntry : con.getRequestProperties().entrySet()) {
            Utils.debugLog(headerEntry.getKey() + ": " + Joiner.on("; ").join(headerEntry.getValue()));
        }

        Utils.debugLog("\nGET RESP HEADERS:");

        for (Map.Entry<String, List<String>> headerEntry : con.getHeaderFields().entrySet()) {
            Utils.debugLog(headerEntry.getKey() + ": " + Joiner.on("; ").join(headerEntry.getValue()));
        }

        Utils.debugLog("\nGET RESP BODY:");
        Utils.debugLog(body);

        return SharePointPostback.parseFormData(body);
    }

    @Override
    public void postForm(String body) throws IOException {
        // Write the body of the POST
        byte[] postBody = toString().getBytes();

        HttpURLConnection con = (HttpURLConnection) new URL(POSTBACK_URL).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/plain");
        con.setRequestProperty("Content-Length", Integer.toString(postBody.length));

        OutputStream output = con.getOutputStream();
        output.write(postBody);
        output.flush();
        output.close();

        con.connect();

        Utils.debugLog("POST REQ HEADERS:");

        for (Map.Entry<String, List<String>> headerEntry : con.getRequestProperties().entrySet()) {
            Utils.debugLog(headerEntry.getKey() + ": " + Joiner.on("; ").join(headerEntry.getValue()));
        }

        Utils.debugLog("\nPOST REQ BODY:");
        Utils.debugLog(body);

        Utils.debugLog("\nPOST RESP HEADERS:");

        for (Map.Entry<String, List<String>> headerEntry : con.getHeaderFields().entrySet()) {
            Utils.debugLog(headerEntry.getKey() + ": " + Joiner.on("; ").join(headerEntry.getValue()));
        }

        Utils.debugLog("\nPOST RESP BODY:");
        Utils.debugLog(CharStreams.toString(new InputStreamReader(con.getInputStream())));
    }
}
