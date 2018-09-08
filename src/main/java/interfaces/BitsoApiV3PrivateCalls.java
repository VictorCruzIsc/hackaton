package interfaces;

import exceptions.ApiException;
import models.requests.trades.Order;
import models.responses.AccountStatus;
import org.json.JSONObject;

public interface BitsoApiV3PrivateCalls {

    AccountStatus accountStatus() throws ApiException;

    JSONObject placeAndOrder(Order order) throws ApiException;

    JSONObject ledger(String queryParameters) throws ApiException;
}
