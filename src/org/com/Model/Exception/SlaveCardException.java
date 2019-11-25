package org.com.Model.Exception;

import java.io.Serializable;

public class SlaveCardException extends Exception implements Serializable {
    public SlaveCardException(String errorMessage) {
        super(errorMessage);
    }
}