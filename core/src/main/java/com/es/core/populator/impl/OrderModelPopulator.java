package com.es.core.populator.impl;

import com.es.core.form.OrderForm;
import com.es.core.model.OrderModel;
import com.es.core.populator.interfaces.Populator;

public class OrderModelPopulator implements Populator<OrderForm, OrderModel> {

    @Override
    public void populate(OrderForm source, OrderModel target) {
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setDeliveryAddress(source.getDeliveryAddress());
        target.setOtherInfo(source.getOtherInfo());
        target.setContactPhoneNo(source.getContactPhoneNo());
    }
}
