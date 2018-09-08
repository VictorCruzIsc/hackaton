package exceptions;

import lombok.Data;

@Data
public class ApiException extends Exception {

    private String errorCode;

    public ApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(Throwable throwable) {
        super(throwable);
        this.errorCode = "0101";
    }
}
