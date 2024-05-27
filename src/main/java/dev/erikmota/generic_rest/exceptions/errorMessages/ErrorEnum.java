package dev.erikmota.generic_rest.exceptions.errorMessages;

import lombok.Getter;

@Getter
public enum ErrorEnum {
    GENERAL(400, "Unknown Error!"),
    NOT_FOUND(404, "Register not found!"),
    PARAMETER_REQUIRED(400, "Mandatory parameter(s) not entered: ");

    private final Integer id;
    private final String message;

    ErrorEnum(Integer id, String message){
        this.id = id;
        this.message = message;
    }
}
