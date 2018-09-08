package clients;

import exceptions.ApiException;
import models.requests.trades.Order;
import models.responses.AccountStatus;
import models.responses.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BitsoClientImpl extends BitsoClient {

    public BitsoClientImpl() {
        super();
    }

    @Override
    public AccountStatus accountStatus() throws ApiException {
        try {
            JSONObject payload = sendPrivateGetRequest("account_status").getJSONObject("payload");
            return new AccountStatus(payload);
        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }

    @Override
    public JSONObject placeAndOrder(Order order) throws ApiException {
        return sendPrivatePostRequest("orders", order.toJsonFormat());
    }

    // TODO: This is a very ugly hack you should accept query parameters instead of hardcode them
    @Override
    public JSONObject ledger(String queryParameters) throws ApiException {
        String ledgerMethod = (queryParameters != null) ? ("ledger?" + queryParameters) : "ledger";
        return sendPrivateGetRequest(ledgerMethod);
    }

    @Override
    public ArrayList<Book> ticker() throws ApiException {
        try {
            ArrayList<Book> books = new ArrayList<>();
            JSONArray payload = sendPublicGetRequest("ticker").getJSONArray("payload");
            int totalBooks = payload.length();
            for (int i = 0; i < totalBooks; i++) {
                Book book = new Book(payload.getJSONObject(i));
                books.add(book);
            }
            return books;
        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
