package dev.erikmota.generic_rest.exceptions;

import dev.erikmota.generic_rest.exceptions.errorMessages.ErrorEnum;
import lombok.Getter;

import java.security.InvalidParameterException;

@Getter
public class ParameterRequiredException extends InvalidParameterException {
    private final ErrorEnum error;
    public ParameterRequiredException(String message){
        super(ErrorEnum.PARAMETER_REQUIRED.getMessage() + message);
        this.error = ErrorEnum.PARAMETER_REQUIRED;
    }
}
