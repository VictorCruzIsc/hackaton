package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Utils {

    public static String streamToString(InputStream inputStream)
            throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));

        String inputLine;

        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }

    public static void checkParameter(String parameterName, Object parameter) {

        Objects.requireNonNull(parameter, String.format("%1$s must not be null", parameterName));

        if (parameter instanceof String) {
            String s = (String) parameter;
            if (s.trim().isEmpty()) {
                throw new IllegalArgumentException(String.format("%1$s is empty", parameterName));
            }
        }
    }
}
