package models.requests.login;

import exceptions.ApiException;
import interfaces.RequestModel;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
public class UserCredentials implements RequestModel {

    private final String userId;
    private final String password;

    @Override
    public String toJsonFormat() throws ApiException {
        try {
            JSONObject json = new JSONObject();
            json.put("userId", userId);
            json.put("password", password);

            return json.toString();

        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
