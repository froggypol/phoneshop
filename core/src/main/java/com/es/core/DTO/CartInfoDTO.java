package com.es.core.DTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartInfoDTO {

    private Long totalQuantity;

    private BigDecimal totalCost;

    @NotNull
    @Min(value = 1L)
    private Long quantityToAdd;

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public Long getQuantityToAdd() {
        return quantityToAdd;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setQuantityToAdd(Long quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
