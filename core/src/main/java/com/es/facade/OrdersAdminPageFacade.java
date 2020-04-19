package com.es.facade;

import com.es.core.model.OrderModel;
import com.es.service.OrderPaginationService;
import com.es.service.OrderService;

import javax.annotation.Resource;
import java.util.List;

public class OrdersAdminPageFacade {

    @Resource
    private OrderPaginationService paginationService;

    @Resource
    private OrderService orderService;

    public List<OrderModel> getOrders(Integer currentPage) {
        return paginationService.listPages(currentPage);
    }

    public OrderModel getOrderInfo(Integer orderNumber) {
        return orderService.getOrderByNumber(orderNumber);
    }

    public OrderModel changeOrderStatus(Integer orderNumber, String status) {
        return orderService.changeStatus(status, orderNumber);
    }

}
