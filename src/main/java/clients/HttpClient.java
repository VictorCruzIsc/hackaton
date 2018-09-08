package clients;

import org.json.JSONException;
import org.json.JSONObject;
import utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

class HttpClient {

    JSONObject sendRequest(HttpURLConnection connection)
            throws IOException, JSONException {
        return executeRequest(connection);
    }

    JSONObject sendAuthorizationRequest(HttpURLConnection connection, String authorizationHeader)
            throws IOException, JSONException {

        return executeRequest(connection, authorizationHeader);
    }

    JSONObject sendAuthorizationRequest(HttpURLConnection connection, String authorizationHeader,
                                        String payload) throws IOException, JSONException {

        return executeRequest(connection, authorizationHeader, payload);
    }

    private JSONObject executeRequest(HttpURLConnection connection, String... parameters)
            throws IOException, JSONException {

        if (parameters.length > 0) {
            connection.setRequestProperty("Authorization", parameters[0]);
        }

        if (parameters.length == 2) {
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(parameters[1]);
            wr.flush();
            wr.close();
        }

        InputStream inputStream;
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            inputStream = connection.getErrorStream();
        }

        String jsonStringFormat = Utils.streamToString(inputStream);

        return new JSONObject(jsonStringFormat);
    }
}
