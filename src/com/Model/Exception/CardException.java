package com.Model.Exception;

import java.io.Serializable;

public class CardException extends Exception implements Serializable {
    public CardException(String errorMessage) {
        super(errorMessage);
    }
}