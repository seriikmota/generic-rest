package dev.erikmota.generic_rest.validations;

public interface IValidations<MODEL> {
    void validate(MODEL data, ValidationAction action);
}
