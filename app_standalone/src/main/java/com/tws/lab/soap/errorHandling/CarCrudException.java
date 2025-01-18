package com.tws.lab.soap.errorHandling;
import jakarta.xml.ws.WebFault;
import lombok.Getter;
@Getter
@WebFault(name = "CarServiceFault")
public class CarCrudException extends Exception {
    private final ErrorBean errorInfo;
    public CarCrudException(String message, ErrorBean errorInfo) {
        super(message);
        this.errorInfo = errorInfo;
    }
    public CarCrudException(String message, ErrorBean errorInfo, Throwable cause) {
        super(message, cause);
        this.errorInfo = errorInfo;
    }
}