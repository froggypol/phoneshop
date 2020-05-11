package com.es.core.dto;

import com.es.core.util.validators.model.ValidQuantityModel;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@ValidQuantityModel
public class ProductInfoDTO {

    @NotEmpty(message = "Empty input!")
    @NotBlank(message = "Blank input!")
    private String modelToAdd;

    @NotNull(message = "Empty input!")
    @Min(value = 1, message = "Quantity should be > 1")
    private Integer quantityToAdd;

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
