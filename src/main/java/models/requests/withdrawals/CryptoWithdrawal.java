package models.requests.withdrawals;

import exceptions.ApiException;
import interfaces.RequestModel;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
public class CryptoWithdrawal implements RequestModel {

    private final String currency;
    private final String address;
    private final String amount;
    private final boolean save;

    @Override
    public String toJsonFormat() throws ApiException {
        try {

            JSONObject jsonFormat = new JSONObject();

            jsonFormat.put("currency", currency);
            jsonFormat.put("address", address);
            jsonFormat.put("amount", amount);
            jsonFormat.put("save", save);

            return jsonFormat.toString();

        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
