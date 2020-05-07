package com.es.core.dto;

import com.es.core.util.validators.model.ValidQuantityModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ValidQuantityModel
public class ProductInfoDTO {

    private String modelToAdd;

    private String quantityToAdd;

    @NotNull
    @Min(value = 1000L)
    private Long productId;

    public ProductInfoDTO() {
    }

    public ProductInfoDTO(String modelToAdd, String quantityToAdd, Long productId) {
        this.modelToAdd = modelToAdd;
        this.quantityToAdd = quantityToAdd;
        this.productId = productId;
    }

    public String getModelToAdd() {
        return modelToAdd;
    }

    public void setModelToAdd(String modelToAdd) {
        this.modelToAdd = modelToAdd;
    }

    public String getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setQuantityToAdd(String quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
