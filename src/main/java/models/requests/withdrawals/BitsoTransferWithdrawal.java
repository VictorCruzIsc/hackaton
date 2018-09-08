package models.requests.withdrawals;

import exceptions.ApiException;
import interfaces.RequestModel;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
public class BitsoTransferWithdrawal implements RequestModel {

    private final String amount;
    private final String currency;
    private final String email;
    private final String phone;
    private final String referralCode;
    private final String notes;

    @Override
    public String toJsonFormat() throws ApiException {
        try {

            JSONObject jsonFormat = new JSONObject();

            jsonFormat.put("amount", amount);
            jsonFormat.put("currency", currency);

            if (notes != null) {
                jsonFormat.put("notes", notes);
            }
            if (email != null) {
                jsonFormat.put("email", email);
            }
            if (phone != null) {
                jsonFormat.put("phone", phone);
            }
            if (referralCode != null) {
                jsonFormat.put("refcode", referralCode);
            }

            return jsonFormat.toString();

        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
