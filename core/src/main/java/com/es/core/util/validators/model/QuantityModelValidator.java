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
    public boolean isValid(ProductInfoDTO productInfoDTO, ConstraintValidatorContext constraintValidatorContext) {
        PhoneModel phoneModel = phoneService.getByModel(productInfoDTO.getModelToAdd());
        if (phoneModel != null) {
            productInfoDTO.setProductId(phoneModel.getId());
        } else {
          return false;
        }
        if (productInfoDTO.getModelToAdd().isEmpty() || productInfoDTO.getModelToAdd().equals(" ")){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid input" + productInfoDTO.getModelToAdd())
                    .addNode("modelToAdd")
                    .addConstraintViolation();
        }
        if (productInfoDTO.getQuantityToAdd().matches("[0-9]+") && !productInfoDTO.getQuantityToAdd().isEmpty()) {
            if (Long.valueOf(productInfoDTO.getQuantityToAdd()) > phoneModel.getStock()) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("Available stock is : "
                        + phoneModel.getStock() + " items. You added" + productInfoDTO.getQuantityToAdd())
                        .addNode("quantityToAdd")
                        .addConstraintViolation();
                return false;
            }
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("invalid input format")
                    .addNode("quantityToAdd")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
