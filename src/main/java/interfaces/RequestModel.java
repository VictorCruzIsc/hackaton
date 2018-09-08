package interfaces;

import exceptions.ApiException;

public interface RequestModel {

    String toJsonFormat() throws ApiException;
}
