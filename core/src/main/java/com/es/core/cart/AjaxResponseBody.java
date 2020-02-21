package com.es.core.cart;

import java.math.BigDecimal;

public class AjaxResponseBody {

    private String msg;

    private String code;

    private BigDecimal totalCost;

    private BigDecimal totalQuantity;

    private BigDecimal quantityToAdd;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setQuantityToAdd(BigDecimal quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
