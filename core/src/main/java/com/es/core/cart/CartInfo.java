package com.es.core.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartInfo {

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

}
