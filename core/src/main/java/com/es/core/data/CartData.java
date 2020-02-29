package com.es.core.data;

import com.es.core.model.CartItemModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartData {

    private List<CartItemModel> cartItemList;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public CartData() {
        cartItemList = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public List<CartItemModel> getCartItemList() {
        return cartItemList;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setCartItemList(List<CartItemModel> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
