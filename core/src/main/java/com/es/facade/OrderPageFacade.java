package com.es.facade;

import com.es.core.converter.OrderFormConverter;
import com.es.core.form.OrderForm;
import com.es.core.model.OrderModel;
import com.es.service.HttpSessionCartService;
import com.es.service.OrderService;

import javax.annotation.Resource;

public class OrderPageFacade {

    @Resource
    private OrderService orderService;

    @Resource
    private HttpSessionCartService cartService;

    @Resource
    private OrderFormConverter orderFormConverter;

    public OrderModel createOrder(OrderForm orderForm) {
        OrderModel orderModel = orderFormConverter.convert(orderForm);
        return orderService.createOrder(cartService.getCart(), orderModel);
    }

    public void placeOrder(OrderModel orderModel) {
        orderService.placeOrder(orderModel);
    }
}
