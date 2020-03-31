package com.es.facade;

import com.es.core.converter.OrderFormConverter;
import com.es.core.form.OrderForm;
import com.es.core.model.OrderModel;
import com.es.core.util.validators.PlaceOrderValidator;
import com.es.service.HttpSessionCartService;
import com.es.service.OrderService;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;

public class OrderPageFacade {

    @Resource
    private PlaceOrderValidator placeOrderValidator;

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

    public boolean hasErrors(OrderModel orderModel, BindingResult bindingResult) {
        placeOrderValidator.validate(orderModel, bindingResult);
        return bindingResult.hasErrors();
    }

    public void placeOrder(OrderModel orderModel) {
        orderService.placeOrder(orderModel);
    }
}
