package com.es.phoneshop.web.controller.pages.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class QuantityValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return QuantityValidator.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object objectToValidate, Errors errors) {
        String quantityToValidate = (String)objectToValidate;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "quantity.invalidFormat",
                "Be careful in number format for quantity.");
        if(quantityToValidate.equals("")) {
            errors.rejectValue("quantity", "quantity.incorrectInput", "Quantity must be filled.");
        }
        if (!quantityToValidate.matches("[0-9]+")) {
            errors.rejectValue("quantity", "quantity.notNumberFormat", "Quantity must have number format.");
        }
    }
}
