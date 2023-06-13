package com.rw.traveller.trips.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class DestinationValidator implements ConstraintValidator<ValidDestination, String> {
    private static final Set<String> SUPPORTED_DESTINATIONS = Set.of("Amsterdam", "Paris", "Beirut");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return SUPPORTED_DESTINATIONS.contains(value);
    }
}
