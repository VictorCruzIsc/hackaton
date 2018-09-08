package models.responses;

import exceptions.ApiException;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

@Data
public class Book {

    private final String book;
    private final String created;
    private final BigDecimal volume;
    private final BigDecimal high;
    private final BigDecimal last;
    private final BigDecimal low;
    private final BigDecimal vwap;
    private final BigDecimal ask;
    private final BigDecimal bid;

    public Book(JSONObject jsonObject) throws ApiException {

        try {
            this.book = jsonObject.getString("book");
            this.created = jsonObject.getString("created_at");
            this.volume = new BigDecimal(jsonObject.getString("volume"));
            this.high = new BigDecimal(jsonObject.getString("high"));
            this.last = new BigDecimal(jsonObject.getString("last"));
            this.low = new BigDecimal(jsonObject.getString("low"));
            this.vwap = new BigDecimal(jsonObject.getString("vwap"));
            this.ask = new BigDecimal(jsonObject.getString("ask"));
            this.bid = new BigDecimal(jsonObject.getString("bid"));
        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }
}
