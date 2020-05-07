package com.es.core.dto;

import com.es.core.util.validators.model.ValidQuantityModel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ValidQuantityModel
public class ProductInfoDTO {

    @NotEmpty(message = "Empty input!")
    private String modelToAdd;

    private Integer quantityToAdd;

    @NotNull
    @Min(value = 1000L)
    private Long productId;

    public ProductInfoDTO() {
    }

    public ProductInfoDTO(String modelToAdd, Integer quantityToAdd, Long productId) {
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

    public Integer getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setQuantityToAdd(Integer quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
