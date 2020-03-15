package com.es.core.model;

import com.es.core.exceptions.OutOfStockException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartModel {

    private List<CartItemModel> cartItems;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public CartModel() {
        cartItems = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public List<CartItemModel> getcartItems() {
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        return cartItems;
    }

    public void addToCart(CartItemModel cartItem) {
        cartItems.add(cartItem);
        recalculateBalance();
    }

    private void recalculateBalance() {
        Long resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
    }

    public Long countQuantity() {
        return getcartItems().stream()
                .mapToLong(CartItemModel::getQuantity)
                .sum();
    }

    public BigDecimal countCost() {
        return getcartItems().stream()
                .reduce(BigDecimal.ZERO,
                        ((bigDecimal, cartItem) -> {
                            BigDecimal totalCost = cartItem.getPhone().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                            return bigDecimal.add(totalCost);
                        }), BigDecimal::add);
    }

    public void updateCart(CartItemModel addedCartItem, CartItemModel cartItemToAdd) {
        if (cartItemToAdd.getQuantity() <= addedCartItem.getPhone().getStock() - addedCartItem.getQuantity()) {
            addedCartItem.setQuantity(cartItemToAdd.getQuantity() + cartItemToAdd.getQuantity());
            cartItems.set(cartItems.indexOf(addedCartItem), addedCartItem);
            recalculateBalance();
        } else {
            throw new OutOfStockException();
        }
    }

    public List<CartItemModel> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemModel> cartItems) {
        this.cartItems = cartItems;
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
