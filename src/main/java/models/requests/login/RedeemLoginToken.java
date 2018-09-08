package models.requests.login;

import exceptions.ApiException;
import interfaces.RequestModel;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
public class RedeemLoginToken implements RequestModel {

    private final String token;
    private String google;
    private String email;
    private String deviceName;
    private String os;
    private String appName;


    @Override
    public String toJsonFormat() throws ApiException {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("token", token);

            if (google != null) {
                jsonObject.put("google", google);
            }

            if (email != null) {
                jsonObject.put("email", email);
            }

            if (deviceName != null) {
                jsonObject.put("deviceName", deviceName);
            }

            if (os != null) {
                jsonObject.put("os", os);
            }

            if (appName != null) {
                jsonObject.put("appName", appName);
            }

            return jsonObject.toString();
        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
