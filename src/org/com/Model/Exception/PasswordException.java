package org.com.Model.Exception;

import java.io.Serializable;

public class PasswordException extends Exception implements Serializable {
    public PasswordException(String errorMessage) {
        super(errorMessage);
    }
}