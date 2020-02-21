package com.es.core.cart;

import com.es.core.model.exceptions.OutOfStockException;
import com.es.core.model.phone.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> cartItemList;

    private Long totalQuantity;

    private BigDecimal totalCost;

    public Cart() {
        cartItemList = new ArrayList<>();
        totalCost = BigDecimal.ZERO;
        totalQuantity = 0L;
    }

    public void addToCart(CartItem cartItem) {
        cartItemList.add(cartItem);
        recalculateBalance();
    }

    public void addNewPhoneToCartItemList(CartItem cartItem) {
        cartItemList.add(cartItem);
    }

    private void recalculateBalance() {
        Long resultQuantity = countQuantity();
        BigDecimal resultCost = countCost();
        setTotalQuantity(resultQuantity);
        setTotalCost(resultCost);
    }

    public Long countQuantity() {
        return getCartItemList().stream()
                                .mapToLong(CartItem::getQuantity)
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

    public void updateCart(CartItem addedCartItem, CartItem cartItemToAdd, Stock stockOfAddedPhone) {
        if (cartItemToAdd.getQuantity() <= stockOfAddedPhone.getStock() - addedCartItem.getQuantity()) {
            addedCartItem.setQuantity(cartItemToAdd.getQuantity());
            cartItemList.set(cartItemList.indexOf(addedCartItem), addedCartItem);
        } else {
            throw new OutOfStockException();
        }
    }

    public List<CartItem> getCartItemList() {
        if (cartItemList == null) {
            cartItemList = new ArrayList<>();
        }
        return cartItemList;
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
