package com.es.facade;

import com.es.core.model.OrderModel;
import com.es.service.OrderService;
import com.es.service.PhoneService;

import javax.annotation.Resource;
import java.util.UUID;

public class OrderOverviewPageFacade {

    @Resource
    private OrderService orderService;

    @Resource
    private PhoneService phoneService;

    public OrderModel getOrder(UUID orderId) {
        OrderModel orderModel = orderService.getOrderById(orderId);
        phoneService.updateProductAfterOrder();
        return orderModel;
    }

}
