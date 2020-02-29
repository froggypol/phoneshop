package com.es.core.model;

import com.es.core.exceptions.OutOfStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartModel {

    private List<CartItemModel> cartItemList;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public CartModel() {
        cartItemList = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public List<CartItemModel> getCartItemList() {
        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
        }
        return cartItemList;
    }

    public void addToCart(CartItemModel cartItem) {
        cartItemList.add(cartItem);
        recalculateBalance();
    }

    private void recalculateBalance() {
        Long resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
    }

    public Long countQuantity() {
        return getCartItemList().stream()
                .mapToLong(CartItemModel::getQuantity)
                .sum();
    }

    public BigDecimal countCost() {
        return getCartItemList().stream()
                .reduce(BigDecimal.ZERO,
                        ((bigDecimal, cartItem) -> {
                            BigDecimal totalCost = cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                            return bigDecimal.add(totalCost);
                        }), BigDecimal::add);
    }

    public void updateCart(CartItemModel addedCartItem, CartItemModel cartItemToAdd) {
        if (cartItemToAdd.getQuantity() <= addedCartItem.getPhone().getStock() - addedCartItem.getQuantity()) {
            addedCartItem.setQuantity(cartItemToAdd.getQuantity());
            cartItemList.set(cartItemList.indexOf(addedCartItem), addedCartItem);
            recalculateBalance();
        } else {
            throw new OutOfStockException();
        }
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }
}
