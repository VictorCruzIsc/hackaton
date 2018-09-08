package models.requests.trades;

import exceptions.ApiException;
import interfaces.RequestModel;
import org.json.JSONException;
import org.json.JSONObject;
import utils.BitsoBooks;
import utils.OrderSide;
import utils.OrderType;
import utils.Utils;

public class Order implements RequestModel {

    private final String book;
    private final String side;
    private final String type;
    private final String major;
    private final String minor;
    private final String price;

    public Order(Builder builder) {
        this.book = builder.book;
        this.side = builder.side;
        this.type = builder.type;
        this.major = builder.major;
        this.minor = builder.minor;
        this.price = builder.price;
    }

    @Override
    public String toJsonFormat() throws ApiException {

        try {

            JSONObject jsonFormat = new JSONObject();

            jsonFormat.put("book", book);
            jsonFormat.put("side", side);
            jsonFormat.put("type", type);

            if (major != null) {
                jsonFormat.put("major", major);
            }

            if (minor != null) {
                jsonFormat.put("minor", minor);
            }

            if (price != null) {
                jsonFormat.put("price", price);
            }

            return jsonFormat.toString();

        } catch (JSONException e) {
            throw new ApiException(e);
        }
    }

    public static class Builder {

        String book;
        String side;
        String type;
        String major;
        String minor;
        String price;

        public Builder() {
        }

        public Builder book(BitsoBooks book) {

            Utils.checkParameter("book", book);
            this.book = book.name().toLowerCase();
            return this;
        }

        public Builder side(OrderSide side) {

            Utils.checkParameter("side", side);
            this.side = side.name().toLowerCase();
            return this;
        }

        public Builder type(OrderType type) {

            Utils.checkParameter("type", type);
            this.type = type.name().toLowerCase();
            return this;
        }

        public Builder major(String major) {

            Utils.checkParameter("major", major);
            this.major = major;
            return this;
        }

        public Builder minor(String minor) {

            Utils.checkParameter("minor", minor);
            this.minor = minor;
            return this;
        }

        public Builder price(String price) {

            Utils.checkParameter("price", price);
            this.price = price;
            return this;
        }

        public Order build() {

            if (major != null && minor != null) {
                throw new IllegalArgumentException("Order must be expressed in terms of major or minor never both");
            }

            if (type.equals("market") && price != null) {
                throw new IllegalArgumentException("Market orders do not need a price to be set");
            }

            if (type.equals("limit")) {
                if (major == null) {
                    throw new IllegalArgumentException("When order type is \"limit\" you should always express major, please unset minor and set major");
                }
                if (price == null) {
                    throw new IllegalArgumentException("Missing price for limit order");
                }
            }

            return new Order(this);
        }
    }
}
