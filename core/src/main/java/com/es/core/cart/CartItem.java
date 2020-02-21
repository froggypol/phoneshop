package com.es.core.cart;

import com.es.core.model.phone.Phone;

import javax.annotation.Resource;
import java.util.Objects;

public class CartItem {

    @Resource
    private Phone phone;

    private Long quantity;

    public CartItem(Long quantity, Phone phone) {
        this.quantity = quantity;
        this.phone = phone;
    }

    public Phone getPhone() {
        return phone;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return phone.equals(cartItem.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }
}
