package org.com.Model.Exception;

import java.io.Serializable;

public class ClientException extends Exception implements Serializable {
    public ClientException(String errorMessage) {
        super(errorMessage);
    }
}