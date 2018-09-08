package interfaces;

import exceptions.ApiException;
import models.requests.login.RedeemLoginToken;
import models.requests.login.UserCredentials;
import models.responses.Book;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface BitsoApiV3PublicCalls {

    ArrayList<Book> ticker() throws ApiException;

}
