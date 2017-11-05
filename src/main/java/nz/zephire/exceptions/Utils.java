package nz.zephire.exceptions;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Utils {

    private static final PrintWriter logFile;

    static {
        try {
            logFile = new PrintWriter(Files.newBufferedWriter(Paths.get("exceptions.log"), Charset.defaultCharset(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void debugLog(String output) {
        logFile.println(output);
        logFile.flush();
    }

    public static void debugLog(Throwable t) {
        t.printStackTrace(logFile);
        logFile.flush();
    }

    public static void setAuth(final String username, final char[] password) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public static Gson getGson() {
        return Converters.registerAll(new GsonBuilder())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

}
