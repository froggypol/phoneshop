package com.es.core.converter;

import com.es.core.form.OrderForm;
import com.es.core.model.OrderModel;
import com.es.core.populator.impl.OrderModelPopulator;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

public class OrderFormConverter implements Converter<OrderForm, OrderModel> {

    private List<OrderModelPopulator> populators;

    @Override
    public OrderModel convert(OrderForm orderForm) {
        OrderModel orderModel = new OrderModel();
        populators.forEach(orderModelPopulator -> orderModelPopulator.populate(orderForm, orderModel));
        return orderModel;
    }

    public List<OrderModelPopulator> getPopulators() {
        return populators;
    }

    public void setPopulators(List<OrderModelPopulator> populators) {
        this.populators = populators;
    }
}
