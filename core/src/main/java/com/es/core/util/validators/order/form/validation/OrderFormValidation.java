package com.es.core.util.validators.order.form.validation;

import com.es.core.form.OrderForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OrderFormValidation implements ConstraintValidator<ValidOrderForm, OrderForm> {

    @Override
    public void initialize(ValidOrderForm validOrderForm) {

    }

    @Override
    public boolean isValid(OrderForm orderForm, ConstraintValidatorContext constraintValidatorContext) {
        String name = orderForm.getFirstName();
        String surname = orderForm.getLastName();
        String address = orderForm.getDeliveryAddress();
        String phoneNumber = orderForm.getContactPhoneNo();
        boolean f = true;
        if ((name != null && surname != null && address != null && phoneNumber != null)) {
            f = true;
        }
        else
            return false;
        if (name == null || name.equals("")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Input for 'Name' field is invalid")
                    .addNode("firstName")
                    .addConstraintViolation();
            f = false;
        }
        if (surname == null || surname.equals("")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Input for 'SurName' field is invalid")
                    .addNode("lastName")
                    .addConstraintViolation();
            f = false;
        }
        if (phoneNumber == null || phoneNumber.equals("") || !phoneNumber.matches("[0-9]+")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Input for 'Phone №' field is invalid : only numbers expected")
                    .addNode("contactPhoneNo")
                    .addConstraintViolation();
            f = false;
        }
        if (address == null || address.equals("")) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Input for 'Address' field is invalid")
                    .addNode("deliveryAddress")
                    .addConstraintViolation();
            f = false;
        }
        return f;
    }
}
