package mains;

import clients.BitsoClient;
import clients.BitsoClientImpl;
import exceptions.ApiException;
import models.requests.trades.Order;
import utils.BitsoBooks;
import utils.OrderSide;
import utils.OrderType;

import java.math.BigDecimal;

public class Hackaton {

    private static BitsoClient bitsoClient;

    public static void main(String args[]) {
        // Here comes the magic
    }

    private static void setUpBitsoClient() {
        bitsoClient = new BitsoClientImpl();
        bitsoClient.setKey(System.getenv("key"));
        bitsoClient.setSecret(System.getenv("secret"));
    }

    private static void ticker() {
        try {
            System.out.println(bitsoClient.ticker());
        } catch (ApiException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void accountStatus() {
        try {
            System.out.println(bitsoClient.accountStatus());
        } catch (ApiException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void placeAnOrder() {

        try {

            // Quiero ejecutar una orden
            Order.Builder builder = new Order.Builder();

            //Example 1:
            builder.book(BitsoBooks.BTC_MXN); // En el libro de órdenes btc_mxn
            builder.side(OrderSide.BUY); // Quiero comprar
            builder.type(OrderType.LIMIT); // El tipo de orden que voy a ejecutar es limitada
            builder.minor("50"); // La cantidad de minor que quiero gastar para comprar major, en este caso gasto MXN, gano BTC.
            builder.price("100000"); // El precio máximo al que estoy dispuesto a pagar

            // Example 2:
            /*builder.book(BitsoBooks.BTC_MXN); // En el libro de órdenes btc_mxn
            builder.side(OrderSide.BUY); // Quiero comprar
            builder.type(OrderType.LIMIT); // El tipo de orden que voy a ejecutar es limitada
            builder.major("0.0005"); // La cantidad de major que quiero comprar, con mis mismos 50 pesos
            builder.price("100000"); // El precio máximo al que estoy dispuesto a pagar*/

            // Example 3:
            /*builder.book(BitsoBooks.BTC_MXN); // En el libro de órdenes btc_mxn
            builder.side(OrderSide.SELL); // Quiero vender
            builder.type(OrderType.LIMIT); // El tipo de orden que voy a ejecutar es limitada
            builder.major("0.0001"); // La cantidad de major, en este caso BTC que quiero vender
            builder.price("200000.50"); // El precio mínimo al que estoy dispuesto a vender*/

            Order order = builder.build();
            System.out.println(bitsoClient.placeAndOrder(order));
        } catch (ApiException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    // Challenge
    private static String fiatToCrypto(BigDecimal fiatValue, BigDecimal expectedTradePrice) {
        return null;
    }

    // Challenge
    private static void sendParametrizedOrder() {

    }
}