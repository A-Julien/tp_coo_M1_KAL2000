package com.Model.Exception;

import java.io.Serializable;

public class StatusDvdException extends Exception implements Serializable {
    public StatusDvdException(String errorMessage) {
        super(errorMessage);
    }
}