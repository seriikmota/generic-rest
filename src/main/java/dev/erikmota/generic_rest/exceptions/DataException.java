package dev.erikmota.generic_rest.exceptions;

import dev.erikmota.generic_rest.exceptions.errorMessages.ErrorEnum;
import lombok.Getter;

@Getter
public class DataException extends RuntimeException {
    private final ErrorEnum error;

    public DataException(String message){
        super(message);
        this.error = ErrorEnum.GENERAL;
    }
    public DataException(ErrorEnum err){
        super(err.getMessage());
        this.error = err;
    }
}
