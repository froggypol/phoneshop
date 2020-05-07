package com.es.core.util.validators.model;

import com.es.core.dto.ProductInfoDTO;
import com.es.core.model.PhoneModel;
import com.es.service.PhoneService;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuantityModelValidator implements ConstraintValidator<ValidQuantityModel, ProductInfoDTO> {

    @Resource
    private PhoneService phoneService;

    @Override
    public void initialize(ValidQuantityModel validModel) {

    }

    @Override
    public boolean isValid(ProductInfoDTO productInfoDTO, ConstraintValidatorContext validatorContext) {
        PhoneModel phoneModel = phoneService.getByModel(productInfoDTO.getModelToAdd());
        if (phoneModel != null) {
            productInfoDTO.setProductId(phoneModel.getId());
        } else {
            setErrorMessageForModelInvalidInput(productInfoDTO, validatorContext);
            return false;
        }
        if (productInfoDTO.getModelToAdd().equals(" ")) {
            setErrorMessageForModelInvalidInput(productInfoDTO, validatorContext);
        }
        if (productInfoDTO.getQuantityToAdd() != null && productInfoDTO.getQuantityToAdd().toString().matches("[0-9]+")) {
                return checkIfQuantityOutOfStock(productInfoDTO, phoneModel, validatorContext);
        } else {
                setErrorMessageForQuantityInvalidFormat(validatorContext);
                return false;
            }
    }

    private void setErrorMessageForModelInvalidInput(ProductInfoDTO productInfoDTO, ConstraintValidatorContext validatorContext) {
        validatorContext.disableDefaultConstraintViolation();
        validatorContext.buildConstraintViolationWithTemplate("Invalid input " + productInfoDTO.getModelToAdd())
                        .addNode("modelToAdd")
                        .addConstraintViolation();
    }

    private void setErrorMessageForQuantityInvalidFormat(ConstraintValidatorContext validatorContext) {
        validatorContext.disableDefaultConstraintViolation();
        validatorContext.buildConstraintViolationWithTemplate("invalid input format")
                        .addNode("quantityToAdd")
                        .addConstraintViolation();
    }

    private boolean checkIfQuantityOutOfStock(ProductInfoDTO productInfoDTO, PhoneModel phoneModel, ConstraintValidatorContext validatorContext) {
        if (Long.valueOf(productInfoDTO.getQuantityToAdd()) > phoneModel.getStock()) {
            setErrorMessageForOutOfStockQuantity(productInfoDTO, phoneModel, validatorContext);
            return false;
        }
        return true;
    }

    private void setErrorMessageForOutOfStockQuantity(ProductInfoDTO productInfoDTO, PhoneModel phoneModel, ConstraintValidatorContext validatorContext) {
        validatorContext.disableDefaultConstraintViolation();
        validatorContext.buildConstraintViolationWithTemplate("Available stock is : "
                + phoneModel.getStock() + " items. You added " + productInfoDTO.getQuantityToAdd())
                        .addNode("quantityToAdd")
                        .addConstraintViolation();
    }
}
