package com.es.facade;

import com.es.core.model.OrderModel;
import com.es.service.OrderService;

import javax.annotation.Resource;
import java.util.UUID;

public class OrderOverviewPageFacade {

    @Resource
    private OrderService orderService;

    public OrderModel getOrder(UUID orderId) {
    return orderService.getOrderById(orderId);
    }

}
