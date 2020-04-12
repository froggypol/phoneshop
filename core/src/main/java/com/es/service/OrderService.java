package com.es.service;

import com.es.core.exceptions.NoSuchOrderException;
import com.es.core.exceptions.OutOfStockException;
import com.es.core.model.CartModel;
import com.es.core.model.OrderModel;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderModel createOrder(CartModel cart, OrderModel orderModel);

    void placeOrder(OrderModel order) throws OutOfStockException;

    OrderModel getOrderById(UUID orderId) throws NoSuchOrderException;

    List<OrderModel> findAll(int limit, int offset);

    OrderModel getOrderByNumber(Integer orderNumber);

    OrderModel changeStatus(String status, Integer orderNumber);
}
