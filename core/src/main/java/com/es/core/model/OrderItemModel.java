package com.es.core.model;

import java.util.Objects;
import java.util.UUID;

public class OrderItemModel {

    private UUID id;

    private PhoneModel phone;

    private OrderModel order;

    private Long quantity;

    public OrderItemModel() {
    }

    public OrderItemModel(PhoneModel phoneModel, Long qnt) {
        this.phone = phoneModel;
        this.quantity = qnt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PhoneModel getPhone() {
        return phone;
    }

    public void setPhone(PhoneModel phone) {
        this.phone = phone;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(final OrderModel order) {
        this.order = order;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemModel that = (OrderItemModel) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
