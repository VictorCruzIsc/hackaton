package clients;

import exceptions.ApiException;
import exceptions.MissingKeysException;
import interfaces.BitsoApiV3PrivateCalls;
import interfaces.BitsoApiV3PublicCalls;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public abstract class BitsoClient extends HttpClient
        implements BitsoApiV3PublicCalls, BitsoApiV3PrivateCalls {

    private final String get = "GET";
    private final String post = "POST";
    private final String userAgent;
    private final String baseUrl;

    @Setter
    private String key;
    @Setter
    private String secret;

    BitsoClient() {
        this("Hackaton2018");
    }

    BitsoClient(String userAgent) {
        this.userAgent = userAgent;
        this.baseUrl = "https://api.bitso.com/v3/";
    }

    JSONObject sendPublicGetRequest(String apiMethod) throws ApiException {
        try {
            JSONObject jsonObject = sendRequest(getConnection(apiMethod));
            if (jsonObject.has("error")) {
                JSONObject jsonError = jsonObject.getJSONObject("error");
                throw new ApiException(jsonError.getString("message"), jsonError.getString("code"));
            }
            return jsonObject;
        } catch (IOException | JSONException e) {
            throw new ApiException(e);
        }
    }

    /*protected JSONObject sendPublicPostRequest(String apiMethod, RequestModel requestModel) throws ApiException {
        return sendPublicPostRequest(apiMethod, requestModel.toJsonFormat());
    }*/

    /*private JSONObject sendPublicPostRequest(String apiMethod, String payload) throws ApiException {
        try {
            String authHeader = "";
            JSONObject jsonObject = sendAuthorizationRequest(postConnection(apiMethod), authHeader, payload);
            if (jsonObject.has("error")) {
                JSONObject jsonError = jsonObject.getJSONObject("error");
                throw new ApiException(jsonError.getString("message"), jsonError.getString("code"));
            }
            return jsonObject;
        } catch (IOException | JSONException e) {
            throw new ApiException(e);
        }
    }*/

    JSONObject sendPrivateGetRequest(String apiMethod) throws ApiException {

        try {

            checkApiKeys();

            String authHeader = buildAuthHeader(key, secret, get, apiMethod);

            JSONObject jsonObject = sendAuthorizationRequest(getConnection(apiMethod), authHeader);

            if (jsonObject.has("error")) {
                JSONObject jsonError = jsonObject.getJSONObject("error");
                throw new ApiException(jsonError.getString("message"), jsonError.getString("code"));
            }

            return jsonObject;

        } catch (IOException | NoSuchAlgorithmException | JSONException | InvalidKeyException | MissingKeysException e) {
            throw new ApiException(e);
        }
    }

    protected JSONObject sendPrivatePostRequest(String apiMethod, String payload)
            throws ApiException {

        try {

            checkApiKeys();

            String authHeader = buildAuthHeader(key, secret, post, apiMethod, payload);

            JSONObject jsonObject = sendAuthorizationRequest(postConnection(apiMethod), authHeader, payload);

            if (jsonObject.has("error")) {
                JSONObject jsonError = jsonObject.getJSONObject("error");
                throw new ApiException(jsonError.getString("message"), jsonError.getString("code"));
            }

            return jsonObject;

        } catch (IOException | NoSuchAlgorithmException | JSONException | InvalidKeyException | MissingKeysException e) {
            throw new ApiException(e);
        }
    }

    private HttpURLConnection getConnection(String apiMethod)
            throws IOException {
        return setUpHttpConnection(apiMethod, userAgent, get);
    }

    private HttpURLConnection postConnection(String apiMethod)
            throws IOException {
        return setUpHttpConnection(apiMethod, userAgent, post);
    }

    private HttpURLConnection setUpHttpConnection(String apiMethod, String userAgent, String httpMethod)
            throws IOException {

        String destination = String.format("%1$s%2$s", baseUrl, apiMethod);

        URL url = new URL(destination);
        HttpURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestMethod(httpMethod);
        connection.setRequestProperty("Content-Type", "application/json");

        return connection;
    }

    private void checkApiKeys()
            throws MissingKeysException {
        if (key == null || secret == null || key.isEmpty() || secret.isEmpty()) {
            throw new MissingKeysException();
        }
    }

    private String buildAuthHeader(String key, String secret, String httpMethod, String apiMethod)
            throws NoSuchAlgorithmException, InvalidKeyException {
        return buildAuthHeader(key, secret, httpMethod, apiMethod, "");
    }

    private String buildAuthHeader(String key, String secret, String httpMethod, String apiMethod, String jsonPayload)
            throws NoSuchAlgorithmException, InvalidKeyException {

        long nonce = nonce();

        String signature = createBitsoSignature(nonce, secret, httpMethod, apiMethod, jsonPayload);

        return String.format("Bitso %s:%s:%s", key, nonce, signature);
    }

    private long nonce() {
        return System.currentTimeMillis();
    }

    private String createBitsoSignature(long nonce, String secret, String httpMethod, String apiMethod,
                                        String jsonPayload) throws InvalidKeyException, NoSuchAlgorithmException {

        String apiMethodComplete = String.format("/v3/%1$s", apiMethod);
        String message = nonce + httpMethod + apiMethodComplete + jsonPayload;

        byte[] secretBytes = secret.getBytes();

        SecretKeySpec localMac = new SecretKeySpec(secretBytes, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(localMac);

        byte[] arrayOfByte = mac.doFinal(message.getBytes());

        BigInteger localBigInteger = new BigInteger(1, arrayOfByte);

        return String.format("%0" + (arrayOfByte.length << 1) + "x", localBigInteger);
    }
}
