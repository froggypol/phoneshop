package com.es.core.util.validators.model;

import com.es.core.dto.ProductInfoDTO;
import com.es.core.exceptions.NotFoundPhoneCustomException;
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
        PhoneModel phoneModel;
        try {
            phoneModel = phoneService.getByModel(productInfoDTO.getModelToAdd());
            productInfoDTO.setProductId(phoneModel.getId());
        } catch (NotFoundPhoneCustomException exception) {
            return false;
        }
        if (productInfoDTO.getQuantityToAdd() != null) {
            return checkIfQuantityOutOfStock(productInfoDTO, phoneModel, validatorContext);
        } else {
            return false;
        }
    }

    private boolean checkIfQuantityOutOfStock(ProductInfoDTO productInfoDTO, PhoneModel phoneModel,
                                              ConstraintValidatorContext validatorContext) {
        if (Long.valueOf(productInfoDTO.getQuantityToAdd()) > phoneModel.getStock()) {
            setErrorMessageForOutOfStockQuantity(phoneModel, validatorContext);
            return false;
        }
        return true;
    }

    private void setErrorMessageForOutOfStockQuantity(PhoneModel phoneModel, ConstraintValidatorContext validatorContext) {
        validatorContext.disableDefaultConstraintViolation();
        validatorContext.buildConstraintViolationWithTemplate("Available stock is : " + phoneModel.getStock() + " items.")
                        .addNode("quantityToAdd")
                        .addConstraintViolation();
    }
}
